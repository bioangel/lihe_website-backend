package com.zhp.authority.dto;


import com.zhp.authority.model.AuthRole;

import java.util.List;

/**
 * Created by zhouhh2 on 2016/8/30.
 */
public class AllAccountRoleDTO {
    private List<AccountDTO> userList;
    private List<AuthRole> allRoleList;

    public List<AccountDTO> getUserList() {
        return userList;
    }

    public void setUserList(List<AccountDTO> userList) {
        this.userList = userList;
    }

    public List<AuthRole> getAllRoleList() {
        return allRoleList;
    }

    public void setAllRoleList(List<AuthRole> allRoleList) {
        this.allRoleList = allRoleList;
    }

    public AllAccountRoleDTO(List<AccountDTO> userList, List<AuthRole> allRoleList) {
        this.userList = userList;
        this.allRoleList = allRoleList;
    }

    public AllAccountRoleDTO() {
    }
}
