package com.zhp.db.base;

/**
 * Created by zhouhh2 on 2018/3/29.
 */
public class CustomDBConfig extends BaseDBConfig {
    private boolean other;

    public boolean isOther() {
        return other;
    }

    public void setOther(boolean other) {
        this.other = other;
    }

    public CustomDBConfig(String driverClassName, String url, String username, String password) {
        super(driverClassName, url, username, password);
    }

    public CustomDBConfig() {
    }
}
