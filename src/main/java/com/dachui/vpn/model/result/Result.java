package com.dachui.vpn.model.result;

import lombok.Getter;

@Getter
public class Result<T> {

    private String code;

    private String msg;

    private Object data;

    public static Result<?> success() {
        Result<?> result = new Result<>();
        result.code = ServiceResponseStatus.SUCCESS.getCode();
        result.msg = ServiceResponseStatus.SUCCESS.getMsg();
        return result;
    }

    public static Result<?> success(Object data) {
        Result<?> result = new Result<>();
        result.code = ServiceResponseStatus.SUCCESS.getCode();
        result.msg = ServiceResponseStatus.SUCCESS.getMsg();
        result.data = data;
        return result;
    }

    public static Result<?> success(ServiceResponseStatus serviceResponseStatus) {
        Result<?> result = new Result<>();
        result.code = serviceResponseStatus.getCode();
        result.msg = serviceResponseStatus.getMsg();
        return result;
    }

    public static Result<?> fail(ServiceResponseStatus serviceResponseStatus, Object data) {
        Result<?> result = new Result<>();
        result.code = serviceResponseStatus.getCode();
        result.msg = serviceResponseStatus.getMsg();
        result.data = data;
        return result;
    }

    public static Result<?> fail() {
        Result<?> result = new Result<>();
        result.code = ServiceResponseStatus.FAIL.getCode();
        result.code = ServiceResponseStatus.FAIL.getMsg();
        return result;
    }

    public static Result<?> fail(ServiceResponseStatus serviceResponseStatus) {
        Result<?> result = new Result<>();
        result.code = serviceResponseStatus.getCode();
        result.msg = serviceResponseStatus.getMsg();
        return result;
    }
}
