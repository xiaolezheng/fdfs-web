package com.lxz.fdfs.support;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import static com.lxz.fdfs.util.HttpUtil.getRemoteHost;

/**
 * Created by xiaolezheng on 16/5/21.
 */
@Slf4j
public class WebInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long startTime = System.currentTimeMillis();
        request.setAttribute("_startTime", startTime);

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // 记录方法耗时
        long startTime = (Long) request.getAttribute("_startTime");
        long endTime = System.currentTimeMillis();
        long costTime = endTime - startTime;
        // url:{1},costTime:{2},ip:{3},agent:{4}
        String agent = request.getHeader("User-Agent");
        String ip = getRemoteHost(request);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        RequestMapping classMapping = handlerMethod.getBeanType().getAnnotation(RequestMapping.class);
        RequestMapping methodMapping = handlerMethod.getMethodAnnotation(RequestMapping.class);
        String template = "{0}|{1}|{2}|{3}|{4}";

        boolean v_c = classMapping != null && classMapping.value() != null && classMapping.value().length > 0;
        boolean v_m = methodMapping.value() != null && methodMapping.value().length > 0;
        if (v_m &&  v_c) {
            String url = classMapping.value()[0].toString()  + methodMapping.value()[0];
            Object[] param = new Object[] { url, costTime, ip, agent };
            String msg = MessageFormat.format(template, param);

            log.info(msg);
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }
}
