package com.zhp.authority.dto;

import java.util.List;

/**
 * Created by zhouhh2 on 2016/9/9.
 */
public class RoleAuthSaveDTO {
    private String rid;

    private String name;

    private Boolean enable;

    private List<String> menuList;

    public RoleAuthSaveDTO(String name, Boolean enable, List<String> menuList) {
        this.name = name;
        this.enable = enable;
        this.menuList = menuList;
    }

    public RoleAuthSaveDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public List<String> getMenuList() {
        return menuList;
    }

    public void setMenuList(List<String> menuList) {
        this.menuList = menuList;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }
}
