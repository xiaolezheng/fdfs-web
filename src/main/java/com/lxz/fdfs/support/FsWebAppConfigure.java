package com.lxz.fdfs.support;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by xiaolezheng on 16/5/21.
 */
@Configuration
public class FsWebAppConfigure extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(new SecureInterceptor()).addPathPatterns("/**");
        registry.addInterceptor(new WebInterceptor()).addPathPatterns("/**");

        super.addInterceptors(registry);
    }
}
