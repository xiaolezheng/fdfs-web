package com.lxz.fdfs.support;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 上传文件简单验证下cookie 是否登录
 *
 * Created by xiaolezheng on 16/5/21.
 */
@Slf4j
public class SecureInterceptor implements HandlerInterceptor {
    private static final String AUTH_COOKIE_NAME = "Authorization";
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String authHeaderContent = httpServletRequest.getHeader(AUTH_COOKIE_NAME);
        if(StringUtils.isNoneEmpty(authHeaderContent)){
            return true;
        }

        Cookie[] cookies = httpServletRequest.getCookies();
        if(ArrayUtils.isNotEmpty(cookies)) {
            for (Cookie cookie : cookies) {
                if (AUTH_COOKIE_NAME.equals(cookie.getName())) {
                    return true;
                }
            }
        }

        throw new FsException("please login in first");
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
