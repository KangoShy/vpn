package com.dachui.vpn.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.dachui.vpn.config.MessageConstants;
import com.dachui.vpn.model.VpnConfigInformation;
import com.dachui.vpn.model.po.GrantOrderRecordPO;
import com.dachui.vpn.model.po.OrderRecordsPO;
import com.dachui.vpn.model.po.UserMessagePO;
import com.dachui.vpn.repository.GrantOrderRecordMapper;
import com.dachui.vpn.repository.OrderRecordsMapper;
import com.dachui.vpn.repository.UcenterMemberMapper;
import com.dachui.vpn.repository.UserMessageMapper;
import com.dachui.vpn.util.AESUtil;
import com.dachui.vpn.util.StringUtil;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;

@Slf4j
@SuppressWarnings("all")
public class ManageServiceHandle {

    @Resource private UcenterMemberMapper ucenterMemberMapper;
    @Resource private OrderRecordsMapper orderRecordsMapper;
    @Resource private UserMessageMapper userMessageMapper;

    // 生成默认用户名
    public static String makeUserName() {
        String start = "用户%s";
        Calendar instance = Calendar.getInstance();
        instance.setTime(new Date());
        long body = instance.getTime().getTime();
        return String.format(start, body);
    }

    // 参数检查
    public static boolean checkStringParam(Object... s) {
        for (Object o : s) {
            if (o == null) {
                return true;
            } else {
                String os = o.toString();
                if (StringUtil.isEmpty(os)) {
                    return true;
                }
            }
        }
        return false;
    }


    // vpn配置信息加密方法
    public static VpnConfigInformation encryPtion(String str) {
        VpnConfigInformation config = new VpnConfigInformation();
        if (StringUtil.isEmpty(str)) {
            return config;
        }
        String encrypt = null;
        try {
            encrypt = AESUtil.Encrypt(str);
        } catch (Exception e) {
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
        } catch (Exception e) {
            log.info("解密失败，异常 = \n{}", e.getMessage());
        }
        if (StringUtil.isEmpty(encrypt)) {
            return config;
        }
        return JSONObject.parseObject(encrypt, config.getClass());
    }


}
