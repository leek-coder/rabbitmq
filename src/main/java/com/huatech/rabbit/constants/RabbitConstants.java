package com.huatech.rabbit.constants;

/**
 * @author like
 * @date 2020-12-03 2:24 下午
 **/
public class RabbitConstants {

    /**
     * 交换机
     */
    public static final String TEST_EXCHANGE = "test_exchange";

    /**
     * 队列
     */
    public static final String TEST_QUEUE = "test_queue";


    /**
     * 下订单队列
     */
    public static final String PLACE_ORDER_QUEUE = "place_order_queue";

    /**
     * 下订单交换机
     */
    public static final String PLACE_ORDER_EXCHANGE = "place_order_exchange";

    public static final String DEAD_ORDER_EXCHANGE = "dead_order_exchange";

    public static final String DEAD_ORDER_QUEUE = "dead_order_queue";

    public static final String DEAD_ROUTE_KEY = "dead_route_key";


}