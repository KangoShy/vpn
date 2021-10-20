package com.dachui.vpn.config;

import com.dachui.vpn.enums.ReturnCodeStatusEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author: Zhouruibin
 * @Date: Created in 14:25 2021/10/15
 * @Description:
 */
@Getter
@Setter
public class AroundException extends RuntimeException {

    private String code;

    private String msg;

    public AroundException(ReturnCodeStatusEnum returnCodeStatusEnum) {
        this.code = returnCodeStatusEnum.getCode();
        this.msg = returnCodeStatusEnum.getMsg();
    }

    public AroundException(ReturnCodeStatusEnum returnCodeStatusEnum, String message) {
        this.code = returnCodeStatusEnum.getCode();
        this.msg = message;
    }

    public AroundException(String code, String message) {
        this.code = code;
        this.msg = message;
    }
}
