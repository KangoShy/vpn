package com.dachui.vpn.controller.test;

import com.dachui.vpn.common.RabbitConstants;
import com.dachui.vpn.controller.test.model.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;
import java.util.UUID;

/* *
 * 说明：
 * 版本：1.0
 * 创建/修改日期：2022/2/25
 * 作者：大锤
 */
@Slf4j
@RestController
public class TestRabbitMQ {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @RequestMapping("/sendMessage")
    public void sendMessage(@RequestParam("message") String message) {

        /* *
         * 生产者到交换机之间的确认机制 [confirm模式]
         * param[0] 相关配置信息
         * param[1] 交换机是否成功接收到消息，成功ack=true 否则为false
         * param[2] ack=false 的失败原因
         */
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            String msgId = null;
            if (correlationData != null) msgId = correlationData.getId();
            if (ack)
                log.info("\n发送消息到交换机成功,MessageId: {}", msgId);
            else
                log.error("\npublishConfirm消息发送到交换机被退回，Id：{}, 退回原因是：{}", msgId, cause);
        });

        // 交换机往队列发送过程中如果出现异常会导致消息直接丢失，设置Mandatory让它重回队列
        rabbitTemplate.setMandatory(true);

         /* *
         * 交换机到队列之间的确认机制 [return模式]
         * param[0] 消息对象
         * param[1] 错误码
         * param[2] 错误信息
         * param[3] 交换机
         * param[4] 路由key
         */
        rabbitTemplate.setReturnCallback((msg, replyCode, replyText, exchange, routingKey) -> {
            log.info("\n消息从交换机发送到队列失败！错误码 = {}，原因 = {}，交换机 = {}， 路由key = {}",
                    replyCode,
                    replyText,
                    exchange,
                    routingKey);
        });

        // 准备对象
        Message buildMsg = Message.builder()
                .createTime(new Date())
                .messageId(UUID.randomUUID().toString())
                .messageSubj(message)
                .build();

        CorrelationData correlation = new CorrelationData();
        correlation.setId(UUID.randomUUID().toString());

        // 发送
        rabbitTemplate.convertAndSend(
                RabbitConstants.ORDER_DIRECT_EX_CHANGE,
                RabbitConstants.ORDER_DIRECT_ROUTING,
                buildMsg,
                correlation);
    }

    private byte[] ObjectToByte(Object obj) {
        byte[] bytes = null;
        try {
            // object to bytearray
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);

            bytes = bo.toByteArray();

            bo.close();
            oo.close();
        } catch (Exception e) {
            System.out.println("translation" + e.getMessage());
            e.printStackTrace();
        }
        return bytes;
    }

}
