package com.huatech.rabbit.controller;

import com.huatech.rabbit.constants.RabbitConstants;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @date 2020-12-03 2:29 下午
 **/
@Component
public class MessageConsumerHandler {

    @RabbitListener(queues = RabbitConstants.TEST_QUEUE)
    public void process(String body, Channel channel, Message message) {
        System.out.println("body"+body);
    }

}