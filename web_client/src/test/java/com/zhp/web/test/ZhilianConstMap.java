package com.zhp.web.test;

/**
 * Created by Administrator on 2018/4/2.
 */
public enum ZhilianConstMap {

    VERIFY("/chk/verify?callback=jsonpCallback&",
            new int[]{47, 104, 63, 191, 69, 148, 99, 71, 130, 101, 12, 4, 232, 188, 109, 80});


    private final String url;

    private final int[] code;

    ZhilianConstMap(String url, int[] code) {
        this.url = url;
        this.code = code;
    }
}
