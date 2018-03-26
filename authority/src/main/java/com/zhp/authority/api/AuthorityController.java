package com.zhp.authority.api;

import com.zhp.authority.dbservice.AuthorityService;
import com.zhp.authority.model.AuthAuthority;
import com.zhp.cache.base.CacheConstants;
import com.zhp.cache.base.CacheOperation;
import com.zhp.common.exception.ErrorCode;
import com.zhp.common.exception.ErrorEntity;
import com.zhp.sys.base.SystemLog;
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

    @SystemLog(action = "AUTH_LIST", group = "AUTHORITY")
    @RequestMapping(value = "get", method = {RequestMethod.GET})
    public ResponseEntity getAllAuth() {
        return ResponseEntity.ok().body(authorityService.findAuthTreeAll());
//        return (List<AuthAuthorityDTO>)cacheManager.getCacheByKey(CacheConstants.AUTH_TREE).getCacheByKey();
    }

    @SystemLog(action = "AUTH_LIST_BY_ID", group = "AUTHORITY")
    @RequestMapping(value = "{rid}", method = {RequestMethod.GET})
    public ResponseEntity getAuthByRoleId(@PathVariable("rid") String rid) {
        Map result = new HashMap();
        result.put("rid",rid);
        result.put("authorList",authorityService.selectAuthByRoleId(rid));
        result.put("allAuthorList",getAllAuth());
        return ResponseEntity.ok().body(result);
    }

    @SystemLog(action = "SAVE", group = "AUTHORITY")
    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public ResponseEntity save(@RequestBody AuthAuthority data) {
        int result = authorityService.insertAuth(data);
        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(),null,""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @SystemLog(action = "UPDATE", group = "AUTHORITY")
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

    @SystemLog(action = "AUTH_LIST_ALL", group = "AUTHORITY")
    @RequestMapping(value = "all", method = {RequestMethod.GET})
    public ResponseEntity getAllAuthNoTree() {
        return ResponseEntity.ok().body(authorityService.findAllAuth());
    }

    @SystemLog(action = "DELETE", group = "AUTHORITY")
    @RequestMapping(value = "delete", method = {RequestMethod.POST})
    public ResponseEntity delete(@RequestBody Map map) {
        authorityService.deleteAuth((List<String>)map.get("aid"));
        mcService.save(CacheConstants.UID_API,authorityService.getUidApi(),CacheConstants.EXPIRE_TIME);
        return ResponseEntity.ok().build();
    }
}
