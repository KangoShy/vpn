package com.dachui.vpn.config;

import com.dachui.vpn.common.RabbitConstants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.amqp.core.Queue;
/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/18
 * 作者：大锤
 */
@Configuration
public class DirectRabbitConfig {

    @Bean
    public Queue orderDirectQueue() {
        // 队列持久化
        return new Queue(RabbitConstants.ORDER_DIRECT_QUEUE, true);
    }

    @Bean
    DirectExchange orderDirectExchange() {
        // 交换机
        return new DirectExchange(RabbitConstants.ORDER_DIRECT_EX_CHANGE, true, false);
    }

    @Bean
    Binding bindingDirect() {
        // 队列绑定交换机
        return BindingBuilder.bind(orderDirectQueue()).to(orderDirectExchange()).with(RabbitConstants.ORDER_DIRECT_ROUTING);
    }



    @Bean
    DirectExchange lonelyDirectExchange() {
        return new DirectExchange("lonelyDirectExchange");
    }

}
