package com.zhp.authority.api;

import com.google.common.collect.ImmutableMap;
import com.zhp.authority.dbservice.AccountService;
import com.zhp.authority.dbservice.AuthorityService;
import com.zhp.authority.dbservice.RoleService;
import com.zhp.authority.dto.AccountDTO;
import com.zhp.authority.dto.AllAccountRoleDTO;
import com.zhp.authority.dto.RoleAccountSaveDTO;
import com.zhp.authority.model.AccountRole;
import com.zhp.authority.model.AuthAccount;
import com.zhp.authority.model.AuthRole;
import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.captcha.cache.CaptchaCache;
import com.zhp.captcha.config.CaptchaConfig;
import com.zhp.common.exception.BadRequestException;
import com.zhp.common.exception.ErrorCode;
import com.zhp.common.exception.ErrorEntity;
import com.zhp.common.utils.CommonUtils;
import com.zhp.common.utils.Md5Util;
import com.zhp.sys.base.AccessConstants;
import com.zhp.sys.base.SystemLog;
import com.zhp.sys.model.LoginVO;
import com.zhp.sys.model.UserLoginDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/account")
public class AccountController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccountService accountService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CacheOperation mcService;

    @Autowired
    private CaptchaCache captchaCache;

    @Autowired(required = false)
    private CaptchaConfig captchaSwitchConfig;

    @SystemLog(action = AccessConstants.LOGIN_ACTION, group = "ACCOUNT")
    @RequestMapping(value = "login", method = {RequestMethod.POST})
    public ResponseEntity login(@Valid @RequestBody UserLoginDTO account) {
        checkCaptcha(account);
        List<AuthAccount> result = accountService.getAccount(account);
        if (result == null || result.size() <= 0) {
            throw new BadRequestException(new ErrorEntity(ErrorCode.USER_NOT_FOUND,
                    ErrorCode.USER_NOT_FOUND.getMessage()));
        }
        List<String> roleIdList = result.stream().map(e -> e.getRoleId()).collect(Collectors.toList());
        if (!accountService.checkLoginUser(account, result.get(0))) {
            throw new BadRequestException(new ErrorEntity(ErrorCode.USERNAME_OR_PASSWORD_ERROR,
                    ErrorCode.USERNAME_OR_PASSWORD_ERROR.getMessage()));
        }
        int time = account.getTime() == 0 || account.getTime() > CacheConstants.EXPIRE_TIME
                ? CacheConstants.EXPIRE_TIME : account.getTime();
        String token = CommonUtils.getUuid();
        mcService.save(token, new LoginVO(result.get(0).getUuid(), result.get(0).getUsername(), time), time);
        return ResponseEntity.ok().body(ImmutableMap.of(AccessConstants.TOKEN, token,
                "menu", authorityService.findLeftMenu(roleIdList),
                "uid", result.get(0).getUuid()));
    }

    private void checkCaptcha(UserLoginDTO account) {
        if (captchaSwitchConfig == null || !captchaSwitchConfig.isEnabled()) {
            return;
        }
        if (account != null) {
            String verifyCode = captchaCache.find(account.getVerifyToken());
            if (verifyCode == null || !verifyCode.equalsIgnoreCase(account.getVerifyCode())) {
                throw new BadRequestException(new ErrorEntity(ErrorCode.INVALID_CAPTCHA,
                        ErrorCode.INVALID_CAPTCHA.getMessage()));
            }
        } else {
            throw new BadRequestException(new ErrorEntity(ErrorCode.INVALID_CAPTCHA,
                    ErrorCode.INVALID_CAPTCHA.getMessage()));
        }

    }

    @SystemLog(action = "SAVE", group = "ACCOUNT")
    @RequestMapping(value = "save", method = RequestMethod.POST)
    public ResponseEntity saveAccount(@RequestBody AuthAccount account) {
        account.setPassword(Md5Util.md5Encode(account.getPassword()));
        account.setEnable(true);
        account.setRegistertime(new Date());
        account.setUuid(CommonUtils.getUuid());
        account.setVersion(0);
        if (accountService.save(account) > 0) {
            return ResponseEntity.ok().build();
        }
        throw new BadRequestException(new ErrorEntity(ErrorCode.CREATE_USER_ERROR,
                ErrorCode.CREATE_USER_ERROR.getMessage()));
    }

    @SystemLog(action = "ROLE_SAVE", group = "ACCOUNT")
    @RequestMapping(value = "role/save", method = RequestMethod.POST)
    public ResponseEntity saveAccountRole(@RequestBody RoleAccountSaveDTO record) {
        roleService.saveAccount(record);
        mcService.save(CacheConstants.UID_API, authorityService.getUidApi(), CacheConstants.EXPIRE_TIME);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public ResponseEntity test() {
        Map<String, String> map = new HashMap<>();
        map.put("message", "ok");
        return ResponseEntity.ok().body(map);
    }

    @SystemLog(action = "USER_LIST", group = "ACCOUNT")
    @RequestMapping(value = "userList", method = RequestMethod.GET)
    public ResponseEntity getUserList() {
        Map<String, Object> userMap = new HashMap<>();
        List<AccountRole> lst = accountService.getAccount().stream()
                .filter(accountRole -> !"admin".equals(accountRole.getUsername())).collect(Collectors.toList());
        lst.forEach(accountRole -> userMap.put(accountRole.getUuid(), accountRole.getUsername()));
        return ResponseEntity.ok().body(userMap);
    }

    @SystemLog(action = "USER_ROLE_LIST", group = "ACCOUNT")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity getAccount() {
        List<AccountRole> lst = accountService.getAccount();
        Map<String, AccountDTO> userMap = new HashMap<>();
        List<AccountDTO> userList = lst.stream()
                .map(e -> {
                    AccountDTO obj = userMap.get(e.getUuid());
                    if (obj == null) {
                        obj = getAccountDTO(userMap, e);
                    } else {
                        List<AuthRole> roles = obj.getRoleList();
                        roles.add(new AuthRole(e.getRid(), e.getRname()));
                    }
                    return obj;
                }).distinct().collect(Collectors.toList());
        List<AuthRole> roleList = roleService.getRolesWithEnable();
        return ResponseEntity.ok().body(new AllAccountRoleDTO(userList, roleList));
    }

    private AccountDTO getAccountDTO(Map<String, AccountDTO> userMap,
                                     AccountRole accountRole) {
        AccountDTO obj = new AccountDTO(accountRole.getUuid(), accountRole.getNickName(),
                accountRole.getEmail(), accountRole.getUsername(), accountRole.getOrganization());
        List<AuthRole> newRoles = new ArrayList<>();
        newRoles.add(new AuthRole(accountRole.getRid(), accountRole.getRname()));
        obj.setRoleList(newRoles);
        userMap.put(accountRole.getUuid(), obj);
        return obj;
    }

    @SystemLog(action = "DELETE", group = "ACCOUNT")
    @RequestMapping(value = "delete", method = RequestMethod.POST)
    public ResponseEntity saveAccountRole(@RequestBody Map map) {
        accountService.deleteAccountById((List<String>) map.get("uid"));
        mcService.save(CacheConstants.UID_API,
                authorityService.getUidApi(), CacheConstants.EXPIRE_TIME);
        return ResponseEntity.ok().build();
    }

}  
