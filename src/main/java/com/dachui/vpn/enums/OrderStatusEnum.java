package com.dachui.vpn.enums;

import lombok.Getter;

@Getter
public enum OrderStatusEnum {

    PAY_YES("1", "已付款"),
    PAY_NO("0", "未付款"),
    PAY_TIMEOUT("2", "已超时")
    ;

    private String code;

    private String msg;

    OrderStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
