package com.dachui.vpn.config;

import com.dachui.vpn.common.RabbitConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/18
 * 作者：大锤
 */
@Configuration
public class DirectRabbitConfig {

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单队列>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
    @Bean
    public Queue orderDirectQueue() {
        // 订单队列
        HashMap<String, Object> map = new HashMap<>();
        map.put("x-max-length", 10);
        map.put("x-dead-letter-exchange", RabbitConstants.DLX_EX_CHANGE);
        map.put("x-dead-letter-routing-key", RabbitConstants.DLX_ROUTING);
        return new Queue(RabbitConstants.ORDER_DIRECT_QUEUE, true, false, false, map);
    }

    @Bean
    DirectExchange orderDirectExchange() {
        // 订单交换机
        return new DirectExchange(RabbitConstants.ORDER_DIRECT_EX_CHANGE, true, false);
    }

    @Bean
    Binding bindingDirect() {
        // 订单队列绑定订单交换机
        return BindingBuilder.bind(orderDirectQueue()).to(orderDirectExchange()).with(RabbitConstants.ORDER_DIRECT_ROUTING);
    }

    // >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>订单队列>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    @Bean
    public Queue DLXQueue() {
        // 死信队列
        return new Queue(RabbitConstants.DLX_QUEUE, true);
    }

    @Bean
    DirectExchange DLXExchange() {
        // 死信队列专用交换机
        return new DirectExchange(RabbitConstants.DLX_EX_CHANGE, true, false);
    }

    @Bean
    Binding bindingDlxQueue2Exchange() {
        // 死信队列绑定到死心交换机
        return BindingBuilder.bind(DLXQueue()).to(DLXExchange()).with(RabbitConstants.DLX_ROUTING);
    }

    /*@Bean
    Binding bindingDlxExchange2orderQueue() {
        // 订单队列绑定到死信交换机
        return BindingBuilder.bind(orderDirectQueue()).to(DLXExchange()).with(RabbitConstants.DLX_ROUTING);
    }*/
}
