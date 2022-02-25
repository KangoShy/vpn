package com.dachui.vpn.common;

import com.alibaba.fastjson.JSONObject;
import com.dachui.vpn.enums.LoginStatusEnum;
import com.dachui.vpn.model.UserInfo;
import com.dachui.vpn.util.JwtUtil;
import com.dachui.vpn.util.RedisUtil;
import com.dachui.vpn.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
public class UserCenterInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisUtil redisUtil;

    private static final Logger log = LoggerFactory.getLogger(UserCenterInterceptor.class);

    public UserCenterInterceptor() {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {
        try {
            StringBuffer requestURL = request.getRequestURL();
            String token = request.getHeader("Authorization");
            if (StringUtil.isEmpty(token)) {
                makeErrorStructure(response);
                return false;
            }
            // 鉴权
            UserInfo userInfo = JwtUtil.parseJwtToken(token);
            if (userInfo == null ||
                    StringUtil.isEmpty(userInfo.getUsername())) {
                throw new RuntimeException("抱歉，请重新登录！");
            }
            // 验证过期时间
            String key = GlobalConstants.UnConfig_Constants.TOKEN_REDIS_CACHE.concat(userInfo.getUsername());
            String cacheToken = (String) redisUtil.get(key);
            if (StringUtil.isEmpty(cacheToken)) {
                // 已过期
                makeErrorStructure(response);
                return false;
            }
            // 获取设备信息
            /*Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String name = headerNames.nextElement();
                if ("user-agent".equals(name)) {
                    userInfo.setLoginEquipment(request.getHeader(name));
                    break;
                }
            }*/
            // 放入ThreadLocal
            UserInfoUtil.setUserInfo(userInfo);
            return true;
        } catch (Exception var) {
            log.error("json解析异常", var);
            makeErrorStructure(response);
            return false;
        }
    }

    /* *
     * 功能:返回给前端的标识
     *
     * 创建/修改日期: 2021/12/29
     */
    private void makeErrorStructure(HttpServletResponse response) throws IOException {
        UserInfoUtil.clear();
        PrintWriter writer = response.getWriter();
        writer.flush();
        writer.append(
                JSONObject.toJSONString(
                        LoginStatusEnum.makeResultMap(LoginStatusEnum.LOGIN_TIMEOUT)));
        writer.close();
    }

    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) {
        UserInfoUtil.clear();
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception e) {
    }

}
