package com.dachui.vpn.common;

import org.springframework.stereotype.Component;

/**
 * 常量类
 */
@Component
public class RabbitConstants {

    // 订单队列名
    public static String ORDER_DIRECT_QUEUE = "orderDirectQueue";

    // 延时队列
    public static String ORDER_TIME_DELAY_QUEUE = "orderTimeDelayQueue";

    // 订单交换机名
    public static String ORDER_DIRECT_EX_CHANGE = "orderDirectExchange";

    // 路由
    public static String ORDER_DIRECT_ROUTING = "orderDirectRouting";

    // DLX队列
    public static String DLX_QUEUE = "dlx.queue";

    // 死信交换机
    public static String DLX_EX_CHANGE = "dlx.exchange";

    // 死信路由
    public static String DLX_ROUTING = "dlx.routing";

}
