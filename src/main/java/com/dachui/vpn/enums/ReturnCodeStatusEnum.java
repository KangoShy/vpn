package com.dachui.vpn.enums;

/**
 * @author: DACHUI
 * @Date: 2021/3/31 10:37
 * @Description:
 */
public enum ReturnCodeStatusEnum {

    SEARCH_SUCCESS("0000","查询成功"),
    NOT_DATA("0001","查询无数据"),
    SEARCH_FAILD("1001","查询失败"),
    EMPTY_PARAM("1004","必传参数为空"),
    SYSTEM_ERROR("9000","系统异常"),
    SUCCESS("200","成功"),
    LOGIN_SUCCESS("1111","登录成功"),
    ACCOUT_OR_PASS_FAIL("2222","账号或密码错误"),
    LOGIN_TOKEN_TIMEOUT("3333","Token验证失败"),
    ACCOUNT_ERROR("4444","用户不存在"),
    SUB_SUCCESS("1020","订阅成功"),
    SUB_FAIL("1040","订阅失败"),
    SUB_ALRE_SUCCESS("1050","您已经订阅过了，无需重复订阅！"),
    ;

    private String code;
    private String msg;

    ReturnCodeStatusEnum(String code, String msg){
        this.msg = msg;
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public static ReturnCodeStatusEnum toEnum(String code) {
        ReturnCodeStatusEnum[] var1 = values();
        int var2 = var1.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            ReturnCodeStatusEnum status = var1[var3];
            if (status.code.equals(code)) {
                return status;
            }
        }

        return null;
    }
}
