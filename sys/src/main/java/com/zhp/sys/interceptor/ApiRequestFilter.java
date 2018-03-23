package com.zhp.sys.interceptor;

import com.zhp.sys.base.AccessConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zhouhh2 on 2016/10/24.
 */
@Component
public class ApiRequestFilter implements Filter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AccessConfig accessConfig;

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        response.setHeader("Access-Control-Allow-Origin", accessConfig.getOrigin());
        response.setHeader("Access-Control-Allow-Methods", accessConfig.getMethod());
        response.setHeader("Access-Control-Max-Age", String.valueOf(accessConfig.getMaxAge()));
        response.setHeader("Access-Control-Allow-Headers", accessConfig.getHeader());
        chain.doFilter(req, res);
    }


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    public void destroy() {}
}


