package com.zhp.authority.dto;

/**
 * Created by zhouhh2 on 2016/8/31.
 */
public class RoleAccountSaveDTO {
    private String id;
    private String uuid;
    private String [] roleList;

    public RoleAccountSaveDTO(String uuid, String[] roleList) {
        this.uuid = uuid;
        this.roleList = roleList;
    }

    public RoleAccountSaveDTO() {
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String[] getRoleList() {
        return roleList;
    }

    public void setRoleList(String[] roleList) {
        this.roleList = roleList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
