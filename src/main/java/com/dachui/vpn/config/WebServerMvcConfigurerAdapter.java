package com.dachui.vpn.config;

import com.dachui.vpn.common.UserCenterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
@Configuration
public class WebServerMvcConfigurerAdapter extends WebMvcConfigurationSupport {

    @Bean
    public UserCenterInterceptor userInterceptor() {
        return new UserCenterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> strings = Arrays.asList("/static/**", "/login", "/toLogin", "/error", "/toIndex", "/sendMessage");
        registry.addInterceptor(userInterceptor())
                .excludePathPatterns(strings);
    }

    // 最重要的一步：在此处指明你在拦截器中排除拦截的静态资源路径指向的是classpath下static路径
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
