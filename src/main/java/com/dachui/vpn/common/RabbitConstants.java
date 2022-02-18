package com.dachui.vpn.common;

import org.springframework.stereotype.Component;

/**
 * 常量类
 */
@Component
public class RabbitConstants {

    // 订单队列名
    public static String ORDER_DIRECT_QUEUE = "orderDirectQueue";

    // 订单交换机名
    public static String ORDER_DIRECT_EX_CHANGE = "orderDirectExchange";

    // 路由
    public static String ORDER_DIRECT_ROUTING = "orderDirectRouting";

}
