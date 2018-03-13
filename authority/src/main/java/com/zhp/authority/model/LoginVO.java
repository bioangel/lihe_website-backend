package com.zhp.authority.model;

import java.io.Serializable;

/**
 * Created by zhouhh2 on 2016/10/26.
 */
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 8904886575998544195L;
    private String uid;
    private int time;

    public LoginVO() {
    }

    public LoginVO(String uid, int time) {
        this.uid = uid;
        this.time = time;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
