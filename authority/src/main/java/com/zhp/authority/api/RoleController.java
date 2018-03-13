package com.zhp.authority.api;

import com.zhp.authority.dbservice.AuthorityService;
import com.zhp.authority.dbservice.RoleService;
import com.zhp.authority.dto.RoleAuthSaveDTO;
import com.zhp.authority.model.AuthRole;
import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.common.exception.ErrorCode;
import com.zhp.common.exception.ErrorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * Created by zhouhh2 on 2016/6/27.
 */
@RestController
@RequestMapping(value = "/role")
public class RoleController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private RoleService roleService;

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CacheOperation mcService;

    @RequestMapping(method = {RequestMethod.GET})
    public List<AuthRole> getRoles() {
        return roleService.getRoles();
    }

    @RequestMapping(value = "switch", method = {RequestMethod.POST})
    public ResponseEntity switchRoleEnable(@RequestBody AuthRole data) {
        int result = roleService.updateRoleByPrimaryKeySelective(data);
        if (result > 0) {
            mcService.save(CacheConstants.UID_API, authorityService.getUidApi(), CacheConstants.EXPIRE_TIME);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(), null, ""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public ResponseEntity save(@RequestBody RoleAuthSaveDTO data) {
        int result = roleService.saveRoleAuth(data);
        if (result > 0) {
            mcService.save(CacheConstants.UID_API, authorityService.getUidApi(), CacheConstants.EXPIRE_TIME);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(), null, ""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "modify", method = {RequestMethod.POST})
    public ResponseEntity modify(@RequestBody RoleAuthSaveDTO data) {
        roleService.modifyRoleAuth(data);
        mcService.save(CacheConstants.UID_API, authorityService.getUidApi(), CacheConstants.EXPIRE_TIME);
        return ResponseEntity.ok().build();
    }

    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    public ResponseEntity delete(@RequestBody Map map) {
        roleService.deleteRoleById((List<String>) map.get("rid"));
        mcService.save(CacheConstants.UID_API, authorityService.getUidApi(), CacheConstants.EXPIRE_TIME);
        return ResponseEntity.ok().build();
    }
}
