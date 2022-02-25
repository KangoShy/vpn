package com.dachui.vpn.common;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
public class GlobalConstants {
    /*
  配置常量
   */
    public static class Config_Constants {

        // token过期时间 （unit-分）
        public static Integer TOKEN_TIMEOUT = 30;


    }

    /*
    非配置常量
     */
    public static class UnConfig_Constants {

        // token参数拼接
        public static String TOKEN_REDIS_CACHE = "Login_Token_By_";
        // mongo like
        public static String MONGO_USER_LIKE = "mind_user_like";
        // mongo commit
        public static String MONGO_USER_COMMIT = "mind_user_commit";



    }
}
