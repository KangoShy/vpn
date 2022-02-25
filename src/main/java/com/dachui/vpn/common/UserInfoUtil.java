package com.dachui.vpn.common;

import com.dachui.vpn.model.UserInfo;

/**
 * @Author: Zhouruibin
 * @Date: Created in 10:11 2021/10/14
 * @Description: 存储 UserInfo 到 ThreadLocal，方便后续使用.
 */
public class UserInfoUtil {

    private static ThreadLocal<UserInfo> userInfoThreadLocal = new ThreadLocal<>();

    /** 用户上下文中放入信息 */
    public static void setUserInfo(UserInfo userInfo) {
        userInfoThreadLocal.set(userInfo);
    }

    /** 获取上下文中的信息 */
    public static UserInfo getUserInfo() {
        return userInfoThreadLocal.get();
    }

    /** 获取上下文中的用户名 */
    public static String getUsername() {
        if (null == userInfoThreadLocal.get()) {
            return null;
        }
        return userInfoThreadLocal.get().getUsername();
    }

    /** 获取上下文中的用户Id */
    public static Long getUserId() {
        if (null == userInfoThreadLocal.get()) {
            return null;
        }
        return userInfoThreadLocal.get().getUserId();
    }

    /** 清空上下文 */
    public static void clear() {
        userInfoThreadLocal.set(null);
    }

}
