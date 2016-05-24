package com.lxz.fdfs.support;

import com.lxz.fdfs.util.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
public class WebLogAspect {

    @Before("execution(public * com.xform.fdfs.api..*.*(..))")
    public void doBefore() {
        ContextHolder.set("startTime", System.currentTimeMillis());
    }

    @AfterReturning(returning = "ret", value = "execution(public * com.xform.fdfs.api..*.*(..))")
    public void doAfterReturning(JoinPoint joinPoint, Object ret) throws Throwable {
        try {
            long startTime = (Long) ContextHolder.get("startTime");
            long costTime = System.currentTimeMillis() - startTime;

            // 接收到请求，记录请求内容
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder
                    .getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            String agent = request.getHeader("User-Agent");
            String ip = HttpUtil.getRemoteHost(request);

            // 记录下请求内容
            log.info("{}|{}|{}|{}|{}|{}", ip, request.getRequestURI(), String.valueOf(costTime),
                    Arrays.toString(joinPoint.getArgs()), ret, agent);
        } finally {
            ContextHolder.clean();
        }
    }


}
