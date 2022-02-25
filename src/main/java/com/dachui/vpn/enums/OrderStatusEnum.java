package com.dachui.vpn.enums;

import lombok.Getter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
public enum OrderStatusEnum {


    //0、等待付款中 1、已付款 2、已超时 3、已付款待发货 4、已发货
    PAY_NO("0", "待付款"),
    PAY_YES("1", "已付款"),
    PAY_TIMEOUT("2", "已超时"),
    PAY_OUT("3", "已付款待发货"),
    PAY_SUCCESS("4", "已发货")
    ;

    private final String code;

    private final String msg;

    OrderStatusEnum(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static String getMessageByCode(String code) {
        for (OrderStatusEnum value : OrderStatusEnum.values()) {
            if (value.getCode().equals(code)) {
                return value.getMsg();
            }
        }
        return null;
    }

}
