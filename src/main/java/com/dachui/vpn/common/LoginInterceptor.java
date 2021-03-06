package com.dachui.vpn.common;

import com.dachui.vpn.common.UserInfoUtil;
import com.dachui.vpn.controller.PathController;
import com.dachui.vpn.model.UserInfo;
import com.dachui.vpn.util.StringUtil;
import com.dachui.vpn.util.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        log.info("进入拦截器，token = {}", token);
        if (StringUtil.isNotBlank(token)){
            if (TokenUtils.verify(token)){
                UserInfo userInfo = TokenUtils.getUserInfo(token);
                UserInfoUtil.setUserInfo(userInfo);
                log.info("请求通过：{}", request.getRequestURL());
                return true;
            }
        }
        log.info("请求被拦截：{}", request.getRequestURL());
        response.sendRedirect("/toLogin");
        return false;
    }
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
    }

}
