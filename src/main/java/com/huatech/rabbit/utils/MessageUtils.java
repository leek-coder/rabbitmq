package com.huatech.rabbit.utils;

import com.huatech.rabbit.constants.RabbitConstants;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author like
 * @date 2020-12-03 2:36 下午
 **/
@Component
public class MessageUtils {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void send(String message) {
        CorrelationData correlationData = new CorrelationData("");
        //交换机，路由key，消息实体
        rabbitTemplate.convertAndSend(RabbitConstants.TEST_EXCHANGE, RabbitConstants.TEST_QUEUE, message,correlationData);
    }

    public void order(String message) {
        CorrelationData correlationData = new CorrelationData("");
        //交换机，路由key，消息实体
        rabbitTemplate.convertAndSend(RabbitConstants.PLACE_ORDER_EXCHANGE, RabbitConstants.PLACE_ORDER_QUEUE, message,correlationData);
    }
}