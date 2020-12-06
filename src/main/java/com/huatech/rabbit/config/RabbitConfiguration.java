package com.huatech.rabbit.config;

import com.huatech.rabbit.constants.RabbitConstants;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author like
 * 发送确认：发送到交换机成功
 * 失败回调：交换机路由到队列时失败
 * @date 2020-12-03 2:24 下午
 **/

@Configuration
public class RabbitConfiguration {


    @Bean
    public SimpleRabbitListenerContainerFactory simpleRabbitListenerContainerFactory() {
        SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory = new SimpleRabbitListenerContainerFactory();
        rabbitListenerContainerFactory.setConnectionFactory(connectionFactory());
        //设置消息手动确认
        rabbitListenerContainerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //设置消息预取的数量
        rabbitListenerContainerFactory.setPrefetchCount(1);
        return rabbitListenerContainerFactory;
    }

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory("47.95.212.30", 5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("leek-coder");
        connectionFactory.setPassword("101396");
        //开启发送方确认模式
        connectionFactory.setPublisherConfirms(true);
        connectionFactory.setPublisherReturns(true);
        return connectionFactory;

    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //发送确认
        rabbitTemplate.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                System.out.println("correlationData" + correlationData.getId());
                System.out.println("ack" + ack);
                System.out.println("cause" + cause);
            }
        });

        //开启mandatory模式（开启失败回调）
        rabbitTemplate.setMandatory(true);

        //失败回调
        rabbitTemplate.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
                //message:发送消息的配置
                System.out.println("message" + message);
                System.out.println("replyCode" + replyCode);
                System.out.println("replyText" + replyText);
                System.out.println("test fix bug  12-04");
                System.out.println("exchange" + exchange);
                System.out.println("routingKey" + routingKey);
            }
        });
        return rabbitTemplate;
    }


    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(RabbitConstants.TEST_EXCHANGE, true, false);
    }

    @Bean
    public Queue queue() {
        return new Queue(RabbitConstants.TEST_QUEUE, true);
    }

    @Bean
    public Binding exchangeBindQueue() {
        return BindingBuilder.bind(queue()).to(directExchange()).with(RabbitConstants.TEST_QUEUE);
    }


    /**
     * 下订单交换机
     *
     * @return
     */
    @Bean
    public DirectExchange placeOrderExchange() {
        return new DirectExchange(RabbitConstants.PLACE_ORDER_EXCHANGE, true, false);
    }

    /**
     * 下订单队列 ：业务队列
     *
     * @return
     */
    @Bean
    public Queue placeOrderQueue() {
        Map<String, Object> map = new HashMap(2);
        map.put("x-dead-letter-exchange", RabbitConstants.DEAD_ORDER_EXCHANGE);
        map.put("x-dead-letter-routing-key", RabbitConstants.DEAD_ROUTE_KEY);
        return new Queue(RabbitConstants.PLACE_ORDER_QUEUE, true, false, false, map);
    }

    /**
     * 绑定交换机与队列
     *
     * @return
     */
    @Bean
    public Binding orderExchangeBind() {
        return BindingBuilder.bind(placeOrderQueue()).to(placeOrderExchange()).with(RabbitConstants.PLACE_ORDER_QUEUE);
    }


    /**
     * 定义订单死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange deadOrderExchange() {
        return new DirectExchange(RabbitConstants.DEAD_ORDER_EXCHANGE, true, false);
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue deadOrderQueue() {
        return new Queue(RabbitConstants.DEAD_ORDER_QUEUE, true, false, false);
    }

    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(deadOrderQueue()).to(deadOrderExchange()).with(RabbitConstants.DEAD_ROUTE_KEY);
    }


}