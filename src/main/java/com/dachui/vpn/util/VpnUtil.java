package com.dachui.vpn.util;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/21
 * 作者：大锤
 */
public class VpnUtil {

    public static boolean hasNullParam(String... str) {
        for (String s : str) {
            if (s == null) {
                return true;
            }else{
                return StringUtil.isEmpty(s);
            }
        }
        return false;
    }

}
