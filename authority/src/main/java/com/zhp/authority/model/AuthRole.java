package com.zhp.authority.model;

public class AuthRole {
    private String id;

    private Integer version;

    private String name;

    private Boolean enable;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public AuthRole() {
    }

    public AuthRole(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public AuthRole(String id, Integer version, String name, Boolean enable) {
        this.id = id;
        this.version = version;
        this.name = name;
        this.enable = enable;
    }
}