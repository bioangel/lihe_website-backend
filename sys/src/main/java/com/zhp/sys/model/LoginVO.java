package com.zhp.sys.model;

import java.io.Serializable;

/**
 * Created by zhouhh2 on 2016/10/26.
 */
public class LoginVO implements Serializable {
    private static final long serialVersionUID = 8904886575998544195L;
    private String uid;
    private String uname;
    private int time;

    public LoginVO() {
    }

    public LoginVO(String uid, String uname, int time) {
        this.uid = uid;
        this.uname = uname;
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

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }
}
