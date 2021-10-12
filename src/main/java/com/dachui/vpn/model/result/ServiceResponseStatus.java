package com.dachui.vpn.model.result;

import lombok.Getter;

@Getter
public enum  ServiceResponseStatus {

    SUCCESS("0000", "查询成功"),
    FAIL("9000", "查询失败"),
    SYSTEM_ERROR("9999", "系统异常"),
    ARGS_PARAM("1004", "传入参数为空或格式错误"),
    SUCCESS_NO_RESULT("0001", "查询成功但无数据")

    ;

    private String code;

    private String msg;

    ServiceResponseStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
