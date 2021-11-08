package com.dachui.vpn.common;

import com.dachui.vpn.enums.ReturnCodeStatusEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.time.ZonedDateTime;

/**
 * @author: DACHUI
 * @Date: 2021/3/11 12:00
 * @Description:
 */

public class Result<Object> {
    private String code;
    private String message;
    private Instant time;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object data;

    public Result() {
        this.time = ZonedDateTime.now().toInstant();
    }

    public Result(ReturnCodeStatusEnum returnCodeStatusEnum) {
        this.time = ZonedDateTime.now().toInstant();
        this.code = returnCodeStatusEnum.getCode();
    }

    public Result(ReturnCodeStatusEnum returnCodeStatusEnum, Object data) {
        this(returnCodeStatusEnum);
        this.data = data;
    }

    public static <Object> Result<Object> success(String msg) {
        Result<Object> res = fail(ReturnCodeStatusEnum.SEARCH_SUCCESS);
        res.message = msg;
        return res;
    }


    public static <Object> Result<Object> success(Object data) {
        return new Result(ReturnCodeStatusEnum.SEARCH_SUCCESS, data);
    }

    public static <Object> Result<Object> success(ReturnCodeStatusEnum returnCodeStatusEnum) {
        return (Result<Object>) fail((ReturnCodeStatusEnum)returnCodeStatusEnum, (Object)null);
    }


    public static <Object> Result<Object> success(ReturnCodeStatusEnum returnCodeStatusEnum, Object data) {
        return new Result(returnCodeStatusEnum, data);
    }




    public static <Object> Result<Object> fail() {
        return new Result(ReturnCodeStatusEnum.SYSTEM_ERROR);
    }


    public static <Object> Result<Object> fail(ReturnCodeStatusEnum returnCodeStatusEnum, Object data) {
        return new Result(returnCodeStatusEnum, data);
    }

    public static <Object> Result<Object> fail(ReturnCodeStatusEnum returnCodeStatusEnum) {
        return (Result<Object>) fail((ReturnCodeStatusEnum)returnCodeStatusEnum, (Object)null);
    }

    public static <Object> Result<Object> fail(Object data) {
        return new Result(ReturnCodeStatusEnum.SYSTEM_ERROR, data);
    }

    public static <Object> Result<Object> fail(String msg) {
        Result<Object> res = fail(ReturnCodeStatusEnum.SYSTEM_ERROR);
        res.message = msg;
        return res;
    }

    public static <Object> Result<Object> instance(ReturnCodeStatusEnum returnCodeStatusEnum){
        Result<java.lang.Object> objectResult = new Result<>();
        objectResult.setCode(returnCodeStatusEnum.getCode());
        objectResult.setMessage(returnCodeStatusEnum.getMsg());
        return (Result<Object>) objectResult;
    }

    public static <Object> Result<Object> instance(ReturnCodeStatusEnum returnCodeStatusEnum, Object data){
        Result<java.lang.Object> objectResult = new Result<>();
        objectResult.setCode(returnCodeStatusEnum.getCode());
        objectResult.setMessage(returnCodeStatusEnum.getMsg());
        objectResult.setData(data);
        return (Result<Object>) objectResult;
    }

    public String getMessage() {
        if (this.message != null && this.message.length() > 0) {
            return this.message;
        } else {
            ReturnCodeStatusEnum serviceResponseStatus  = ReturnCodeStatusEnum.toEnum(this.getCode());
            return null == serviceResponseStatus ? "" : serviceResponseStatus.getMsg();
        }
    }

    @JsonIgnore
    public boolean isSuccess() {
        return this.code != null && this.code.startsWith("0");
    }

    @JsonIgnore
    public boolean isFail() {
        return !this.isSuccess();
    }

    public String getCode() {
        return this.code;
    }

    public Instant getTime() {
        return this.time;
    }

    public Object getData() {
        return this.data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setTime(Instant time) {
        this.time = time;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
