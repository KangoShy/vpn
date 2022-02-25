package com.dachui.vpn.enums;

import lombok.Getter;

/**
 * @Author: Zhouruibin
 * @Date: Created in 20:12 2021/10/13
 * @Description: RedisKey枚举类
 */
@Getter
public enum  RedisConstantsKeyEnum {

    PARENT_KEY("parent::key", null, "父KEY"),

    USER_KNOW_KEY("user::know::key", 60 * 5L, "用户须知"),
    COM_BO_KEY("com::bo::key", 24 * 60 * 60L, "套餐列表"),
    ORDER_CACHE_KEY("order::", 1L, "生成订单"),

    ;
    private final String    key;         // 缓存key
    private final Long      cacheTime;   // 过期时间
    private final String    desc;        // 描述
    RedisConstantsKeyEnum(String key, Long cacheTime, String desc) {
        this.key = key;
        this.desc = desc;
        this.cacheTime = cacheTime;
    }

    // 倒计时处理订单的时间
    public static Long getDescTime() {
        // 如果使用Timer类，缓存时间 * 1000
        return RedisConstantsKeyEnum.ORDER_CACHE_KEY.getCacheTime();
    }
}
