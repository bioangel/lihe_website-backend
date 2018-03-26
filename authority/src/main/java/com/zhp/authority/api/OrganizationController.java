package com.zhp.authority.api;

import com.zhp.authority.dbservice.AuthOrganizationService;
import com.zhp.authority.model.AuthOrganization;
import com.zhp.common.exception.ErrorCode;
import com.zhp.common.exception.ErrorEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by cs on 17-3-22.
 */
@RestController
@RequestMapping(value = "/org")
public class OrganizationController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    AuthOrganizationService authOrganizationService;

    @RequestMapping(value = "save", method = {RequestMethod.POST})
    public ResponseEntity save(@RequestBody AuthOrganization authOrganization) {
        int result = authOrganizationService.saveOrg(authOrganization);
        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(), null, ""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "delete", method = {RequestMethod.GET})
    public ResponseEntity delete(@RequestParam("id") Integer id) {
        int result = authOrganizationService.deleteOrg(id);
        if (result > 0) {
            return ResponseEntity.ok("{}");
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(), null, ""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "update", method = {RequestMethod.POST})
    public ResponseEntity update(@RequestBody AuthOrganization authOrganization) {
        int result = authOrganizationService.updateOrg(authOrganization);
        if (result > 0) {
            return ResponseEntity.ok().build();
        }
        return new ResponseEntity<>(new ErrorEntity(ErrorCode.SYSTEM_ERROR,
                ErrorCode.SYSTEM_ERROR.getMessage(), null, ""), ErrorCode.SYSTEM_ERROR.getStatus());
    }

    @RequestMapping(value = "getById", method = {RequestMethod.GET})
    public ResponseEntity getById(@RequestParam("id") Integer id) {
        return ResponseEntity.ok().body(authOrganizationService.getOrgById(id));
    }

    @RequestMapping(value = "getList", method = {RequestMethod.GET})
    public ResponseEntity getList() {
        return ResponseEntity.ok().body(authOrganizationService.getOrgList());
    }
}
