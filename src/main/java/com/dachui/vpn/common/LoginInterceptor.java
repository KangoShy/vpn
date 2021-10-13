package com.dachui.vpn.common;

import com.dachui.vpn.util.StringUtil;
import com.dachui.vpn.util.TokenUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object hander) throws IOException {
        if (hander instanceof ResourceHttpRequestHandler) {
            return true;
        }
        response.setCharacterEncoding("UTF-8");
        String token = request.getHeader("Authorization");

        if (StringUtil.isNotBlank(token)){
            if (TokenUtils.verify(token)){
                log.info("请求通过：{}", request.getRequestURL());
                return true;
            }
        }
        log.info("请求被拦截：{}", request.getRequestURL());
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
            response.setHeader("REDIRECT", "REDIRECT");
        }else{
            response.sendRedirect("/path/toLogin");
        }
        return false;
    }
}