package com.zhp.sys.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by zhouhh2 on 2018/3/23.
 */
public class SysUtils {

    public static String getIp(HttpServletRequest request) {
        if (request.getHeader("x-forwarded-for") == null) {
            return request.getRemoteAddr();
        }
        return request.getHeader("x-forwarded-for");
    }
}
