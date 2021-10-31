package com.dachui.vpn.service;

import com.alibaba.fastjson.JSONObject;
import com.dachui.vpn.model.VpnConfigInformation;
import com.dachui.vpn.util.AESUtil;
import com.dachui.vpn.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ManageServiceHandle {

    // vpn配置信息加密方法
    public static VpnConfigInformation encryPtion(String str) {
        VpnConfigInformation config = new VpnConfigInformation();
        if (StringUtil.isEmpty(str)) {
            return config;
        }
        String encrypt = null;
        try {
            encrypt = AESUtil.Encrypt(str);
        }catch (Exception e) {
            log.info("加密失败，异常 = \n{}", e.getMessage());
        }
        if (StringUtil.isEmpty(encrypt)) {
            return config;
        }
        return JSONObject.parseObject(encrypt, config.getClass());
    }


    // vpn配置信息解密方法
    public static VpnConfigInformation decryPtion(String str) {
        VpnConfigInformation config = new VpnConfigInformation();
        if (StringUtil.isEmpty(str)) {
            return config;
        }
        String encrypt = null;
        try {
            encrypt = AESUtil.Decrypt(str);
        }catch (Exception e) {
            log.info("解密失败，异常 = \n{}", e.getMessage());
        }
        if (StringUtil.isEmpty(encrypt)) {
            return config;
        }
        return JSONObject.parseObject(encrypt, config.getClass());
    }


}
