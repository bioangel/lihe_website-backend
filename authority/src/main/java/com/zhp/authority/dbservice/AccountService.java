package com.zhp.authority.dbservice;

import com.zhp.authority.dao.AuthAccountMapper;
import com.zhp.authority.dto.RoleAccountSaveDTO;
import com.zhp.authority.model.AccountRole;
import com.zhp.authority.model.AuthAccount;
import com.zhp.common.utils.Md5Util;
import com.zhp.security.utils.SecurityUtils;
import com.zhp.sys.model.UserLoginDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by zhouhh2 on 2016/6/27.
 */
@Service
@Transactional
public class AccountService {
    @Autowired
    private AuthAccountMapper accountDao;

    @Autowired
    private RoleService roleService;

    @Autowired
    private SecurityUtils securityUtils;

    public int save(AuthAccount account) {
        if (account.getRoleIdList() != null && account.getRoleIdList().size() > 0) {
            roleService.saveAccountByCreateUser(new RoleAccountSaveDTO(account.getUuid(),
                    account.getRoleIdList().toArray(new String[]{})));
        }
        return accountDao.insertSelective(account);
    }

    public List<AuthAccount> getAccount(UserLoginDTO account) {
        return accountDao.selectByUsername(account.getUsername());
    }

    public List<AuthAccount> getAccountByRole(String roleId) {
        return accountDao.selectByRoleId(roleId);
    }

    public List<AuthAccount> getAccount(String uid) {
        return accountDao.selectByUid(uid);
    }

    public AuthAccount getAccountByUid(String uid) {
        List<AuthAccount> account = accountDao.selectByUid(uid);
        if (account.isEmpty()) {
            return null;
        }
        return account.get(0);
    }

    public AuthAccount selectByPrimaryKey(String uid) {
        return accountDao.selectByPrimaryKey(uid);
    }

    public List<AccountRole> getAccount() {
        return accountDao.selectAccountRole();
    }

    public void deleteAccountById(List<String> uidList) {
        accountDao.deleteAccountById(uidList);
        accountDao.deleteAccountRoleById(uidList);
    }

//    public boolean checkADInfo(String itCode, String password) {
//        String email = itCode + "@lenovo.com";
//        UserCheck userCheck = new UserCheck();
//
//        try {
//            return userCheck.checkUserAccount(email, password);
//        } catch (Exception e) {
//            return false;
//        }
//    }

    public boolean checkLoginUser(UserLoginDTO login, AuthAccount account) {
//        if ("admin".equalsIgnoreCase(login.getUsername())
//                || login.getAd() == 0
//                || !securityUtils.getAd()) {
            return Md5Util.md5Encode(login.getPassword()).equalsIgnoreCase(account.getPassword());
//        }
//        return this.checkADInfo(login.getUsername(), login.getPassword());
    }

    public List<String> getOrgUser(String itCode) {
        return accountDao.selectSameOryByUsername(itCode);
    }
}
