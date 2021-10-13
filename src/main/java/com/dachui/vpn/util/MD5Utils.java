package com.dachui.vpn.util;

/**
 * @author: DACHUI
 * @Date: 2021/3/7 13:59
 * @Description:
 */
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

    /**
     * MD5加密字符串
     *
     * @param str
     * @return
     */
    public static String getMD5(String str) {
        if (StringUtil.isNotEmpty(str)) {
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");
                md.update(str.getBytes());
                byte b[] = md.digest();
                int i;
                StringBuffer buf = new StringBuffer("");
                for (int offset = 0; offset < b.length; offset++) {
                    i = b[offset];
                    if (i < 0)
                        i += 256;
                    if (i < 16)
                        buf.append("0");
                    buf.append(Integer.toHexString(i));
                }
                return buf.toString();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    /**
     * 获取32位大写
     *
     * @param str
     * @return
     */
    public static String getMD5_32_upper(String str) {
        if (StringUtil.isNotEmpty(str))
            return getMD5(str).toUpperCase();
        return "";
    }

    /**
     * 获取32位小写
     *
     * @param str
     * @return
     */
    public static String getMD5_32_lower(String str) {
        if (StringUtil.isNotEmpty(str))
            return getMD5(str).toLowerCase();
        return "";
    }

    /**
     * 获取16位大写
     *
     * @param str
     * @return
     */
    public static String getMD5_16_upper(String str) {
        if (StringUtil.isNotEmpty(str))
            return getMD5(str).substring(8, 24).toUpperCase();
        return "";
    }

    /**
     * 获取16位小写
     *
     * @param str
     * @return
     */
    public static String getMD5_16_lower(String str) {
        if (StringUtil.isNotEmpty(str))
            return getMD5(str).substring(8, 24).toLowerCase();
        return "";
    }
}