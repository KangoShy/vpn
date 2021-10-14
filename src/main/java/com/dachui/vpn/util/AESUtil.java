package com.dachui.vpn.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Zhouruibin
 * @Date: Created in 20:46 2021/10/12
 * @Description:
 */
public class AESUtil {

    private static final String AES_KEY = "jkl;POIU1234++==";

    // 加密
    public static String Encrypt(String sSrc) throws Exception {
        byte[] raw = AES_KEY.getBytes(StandardCharsets.UTF_8);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");//"算法/模式/补码方式"
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
        byte[] encrypted = cipher.doFinal(sSrc.getBytes(StandardCharsets.UTF_8));
        return new BASE64Encoder().encode(encrypted);//此处使用BASE64做转码功能，同时能起到2次加密的作用。
    }

    // 解密
    public static String Decrypt(String sSrc) throws Exception {
        try {
            byte[] raw = AES_KEY.getBytes(StandardCharsets.UTF_8);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(sSrc);//先用base64解密
            try {
                byte[] original = cipher.doFinal(encrypted1);
                return new String(original, StandardCharsets.UTF_8);
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        /*
         * 此处使用AES-128-ECB加密模式，key需要为16位。
         */
        // 需要加密的字串
        String cSrc = "123";
        System.out.println(cSrc);
        // 加密
        String enString = AESUtil.Encrypt(cSrc);
        System.out.println("加密后的字串是：" + enString);

        // 解密
        String DeString = AESUtil.Decrypt(enString);
        System.out.println("解密后的字串是：" + DeString);
    }

}
