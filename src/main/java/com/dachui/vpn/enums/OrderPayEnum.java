package com.dachui.vpn.enums;

import lombok.Getter;

/**
 * <p>
 * 支付方式枚举
 * </p>
 *
 * @author root0day
 * @since 2021/10/19
 */
@Getter
public enum OrderPayEnum {

    ZFB("zfb", "支付宝"),
    WET("wechat", "微信");

    private final String code;
    private final String msg;

    OrderPayEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
