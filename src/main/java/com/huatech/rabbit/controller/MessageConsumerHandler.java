package com.huatech.rabbit.controller;

import com.huatech.rabbit.constants.RabbitConstants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author like
 * @date 2020-12-03 2:29 下午
 **/
@Component
public class MessageConsumerHandler {

    @RabbitListener(queues = RabbitConstants.TEST_QUEUE, containerFactory = "simpleRabbitListenerContainerFactory")
    public void process(String body, Channel channel, Message message) {
        System.out.println("body" + body);
        try {
            //消息确认
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
            //批量退回
            try {
                //是否重回消息队列
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, true);
                //消息退回：单条退回
                channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

    }

    /**
     * 监听下单队列
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = RabbitConstants.PLACE_ORDER_QUEUE, containerFactory = "simpleRabbitListenerContainerFactory")
    public void processOrder(Channel channel, Message message) throws Exception {
        System.out.println("消息拒绝");
        //是否重回消息队列,消息拒绝不重回队列
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }

    /**
     * 死信队列
     *
     * @param channel
     * @param message
     */
    @RabbitListener(queues = RabbitConstants.DEAD_ORDER_QUEUE, containerFactory = "simpleRabbitListenerContainerFactory")
    public void processDeadOrder(Message message, Channel channel) throws Exception {

        System.out.println("死信队列收到信息为：" + new String(message.getBody(), "UTF-8"));
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);

    }

}