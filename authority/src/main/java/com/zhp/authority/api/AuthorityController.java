package com.zhp.authority.api;

import com.zhp.authority.dbservice.AuthorityService;
import com.zhp.authority.dto.AuthAuthorityDTO;
import com.zhp.authority.model.AuthAuthority;
import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.common.exception.ErrorCode;
import com.zhp.common.exception.ErrorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouhh2 on 2016/6/27.
 */
@RestController
@RequestMapping(value = "/authority")
public class AuthorityController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AuthorityService authorityService;

    @Autowired
    private CacheOperation mcService;

    @RequestMapping(value = "get", method = {RequestMethod.GET})
    public List<AuthAuthorityDTO> getAllAuth() {
        return authorityService.findAuthTreeAll();
//        return (List<AuthAuthorityDTO>)cacheManager.getCacheByKey(CacheConstants.AUTH_TREE).getCacheByKey();
    }

    @RequestMapping(value = "{rid}", method = {RequestMethod.GET})
    public Map getAuthByRoleId(@PathVariable("rid") String rid) {
        Map result = new HashMap();
        result.put("rid",rid);
        result.put("authorList",authorityService.selectAuthByRoleId(rid));
        result.put("allAuthorList",getAllAuth());
        return result;
    }

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public ResponseEntity save(@RequestBody AuthAuthority data) {
        int result = authorityService.insertAuth(data);
        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(),null,""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public ResponseEntity update(@RequestBody AuthAuthority data) {
        int result = authorityService.updateAuth(data);
        if (result > 0) {
            mcService.save(CacheConstants.UID_API,authorityService.getUidApi(),CacheConstants.EXPIRE_TIME);
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(),null,""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "all", method = {RequestMethod.GET})
    public List<AuthAuthority> getAllAuthNoTree() {
        return authorityService.findAllAuth();
    }

    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    public ResponseEntity delete(@RequestBody Map map) {
        authorityService.deleteAuth((List<String>)map.get("aid"));
        mcService.save(CacheConstants.UID_API,authorityService.getUidApi(),CacheConstants.EXPIRE_TIME);
        return ResponseEntity.ok().build();
    }
}
