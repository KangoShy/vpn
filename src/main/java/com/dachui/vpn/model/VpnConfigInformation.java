package com.dachui.vpn.model;

import lombok.Getter;
import lombok.Setter;

/**
 * 核心数据 采用AES加密
 */

@Getter
@Setter
public class VpnConfigInformation {

    // 地址
    private String address;
    // 端口
    private String port;
    // 用户名
    private String userAccount;
    // 密码
    private String password;
    // 开通时间
    private String openTime;
    // 失效时间
    private String failureTime;
    // 内存
    private String memory;
    // 带宽
    private String bandwidth;
    // 硬盘
    private String hardDisk;
    // 核数
    private String auditing;

}
