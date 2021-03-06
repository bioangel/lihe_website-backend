package com.zhp.authority.dto;


import com.zhp.authority.model.AuthRole;

import java.util.List;

/**
 * Created by zhouhh2 on 2016/8/30.
 */
public class AccountDTO {
    private String uuid;
    private String nickName;
    private String email;
    private String username;
    private String organization;
    private List<AuthRole> roleList;

    public AccountDTO() {
    }

    public AccountDTO(String uuid, String nickName, String email, String username, String organization) {
        this.uuid = uuid;
        this.nickName = nickName;
        this.email = email;
        this.username = username;
        this.organization = organization;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<AuthRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<AuthRole> roleList) {
        this.roleList = roleList;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
