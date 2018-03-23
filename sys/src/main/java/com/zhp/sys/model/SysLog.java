package com.zhp.sys.model;

import java.util.Date;

public class SysLog {
    private Long id;

    private String uid;

    private String ip;

    private String action;

    private Date actionTime;

    private Long actionCostTime;

    private String actionGroup;

    private String actionStatus;

    private String actionStatusDesc;

    private String businessStatus;

    private String businessStatusDesc;

    public SysLog() {
    }

    public SysLog(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid == null ? null : uid.trim();
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action == null ? null : action.trim();
    }

    public Date getActionTime() {
        return actionTime;
    }

    public void setActionTime(Date actionTime) {
        this.actionTime = actionTime;
    }

    public Long getActionCostTime() {
        return actionCostTime;
    }

    public void setActionCostTime(Long actionCostTime) {
        this.actionCostTime = actionCostTime;
    }

    public String getActionGroup() {
        return actionGroup;
    }

    public void setActionGroup(String actionGroup) {
        this.actionGroup = actionGroup == null ? null : actionGroup.trim();
    }

    public String getActionStatus() {
        return actionStatus;
    }

    public void setActionStatus(String actionStatus) {
        this.actionStatus = actionStatus == null ? null : actionStatus.trim();
    }

    public String getActionStatusDesc() {
        return actionStatusDesc;
    }

    public void setActionStatusDesc(String actionStatusDesc) {
        this.actionStatusDesc = actionStatusDesc == null ? null : actionStatusDesc.trim();
    }

    public String getBusinessStatus() {
        return businessStatus;
    }

    public void setBusinessStatus(String businessStatus) {
        this.businessStatus = businessStatus == null ? null : businessStatus.trim();
    }

    public String getBusinessStatusDesc() {
        return businessStatusDesc;
    }

    public void setBusinessStatusDesc(String businessStatusDesc) {
        this.businessStatusDesc = businessStatusDesc == null ? null : businessStatusDesc.trim();
    }
}