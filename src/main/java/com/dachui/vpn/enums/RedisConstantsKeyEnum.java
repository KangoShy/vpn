package com.dachui.vpn.enums;

import lombok.Getter;

/**
 * @Author: Zhouruibin
 * @Date: Created in 20:12 2021/10/13
 * @Description:
 */
@Getter
public enum  RedisConstantsKeyEnum {

    USER_KNOW_KEY("user::know::key", 60 * 3L, "用户须知"),
    COM_BO_KEY("com::bo::key", 24 * 60 * 60L, "套餐列表")


    ;
    private String key;

    private Long cacheTime;

    private String desc;

    RedisConstantsKeyEnum(String key, Long cacheTime, String desc) {
        this.key = key;
        this.desc = desc;
        this.cacheTime = cacheTime;
    }
}
