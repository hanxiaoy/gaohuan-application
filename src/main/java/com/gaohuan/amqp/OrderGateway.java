package com.gaohuan.amqp;

import org.springframework.messaging.Message;

import java.util.List;



public interface OrderGateway {

    /**
     * Processes Order Request
     *
     * @param message SI Message covering Order payload.
     */
    void processOrderRequest(Message<List<Order>> message);
}