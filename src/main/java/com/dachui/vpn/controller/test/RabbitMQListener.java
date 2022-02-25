package com.dachui.vpn.controller.test;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.lang.model.util.ElementScanner6;
import java.io.IOException;
import java.net.CacheRequest;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/25
 * 作者：大锤
 */
@Slf4j
@Component
public class RabbitMQListener {

    private static int count = 0;

    @RabbitHandler
    @RabbitListener(queues = {"orderDirectQueue"})
    private void getMessage(Message message, Channel channel) throws IOException {
        long deliveryTag = message.getMessageProperties().getDeliveryTag();
        try{
            // 接受消息
            String acceptMsg = new String(message.getBody());
            log.info("\n接收到消息！message = {}", acceptMsg);

            // 处理业务
            //Thread.sleep(2000);

            // 模拟第一次处理发生异常，重试一次之后才完成消费
            //count++;
            //int i = 1 / 0;

            // 手动签收 param[0] 消息当前标识 param[1] 允许多条消息被签收
            channel.basicAck(deliveryTag, true);
            log.info(" >>> 手动签收完成！");
        }catch (Exception e) {
            // param[0] 消息当前标识 param[1]允许多条消息被签收 param[2] broker重发消息给消费端
            channel.basicNack(deliveryTag, true, true);
            log.info("处理业务时发生异常，重新放回队列中！异常 = {}", e.getMessage());
        }
    }



    @RabbitHandler
    @RabbitListener(queues = {"dlx.queue"})
    private void getDlxMessage(Message message, Channel channel) throws IOException {
        log.info("死信队列消费消息 = \n {}", new String(message.getBody()));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }
}
