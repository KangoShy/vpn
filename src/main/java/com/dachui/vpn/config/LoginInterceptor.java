package com.dachui.vpn.config;

import com.dachui.vpn.util.StringUtil;
import com.dachui.vpn.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: Zhouruibin
 * @Date: Created in 19:07 2021/10/13
 * @Description:
 */
@Slf4j
@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
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
            response.sendRedirect("/toLogin");
        }
        return false;
    }
}
