package com.dachui.vpn.common;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ConcurrentTaskExecutor;
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

@Configuration
public class WebConfiguration implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor tokenInterceptor;

    /**
     * 解决跨域请求
     *
     * @param registry
     */
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedHeaders("*")
                .allowedMethods("*")
                .allowedOriginPatterns("*")
                .allowCredentials(true);
    }

    /**
     * 异步请求配置
     *
     * @param configurer
     */
    public void configureAsyncSupport(AsyncSupportConfigurer configurer) {
        configurer.setTaskExecutor(new ConcurrentTaskExecutor(Executors.newFixedThreadPool(3)));
        configurer.setDefaultTimeout(30000);
    }

    /**
     *
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePath = new ArrayList<>();
        excludePath.add("/favicon.ico");
        excludePath.add("/login");
        excludePath.add("/error");

        //static file
        excludePath.add("/static/**");

        registry.addInterceptor(tokenInterceptor)
                .excludePathPatterns(excludePath)
                .addPathPatterns("/**");
        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
