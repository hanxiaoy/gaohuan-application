package com.gaohuan.amqp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;



@Component("orderProcessService")
public class OrderProcessService implements ProcessService<Order> {

    private final Logger logger = LoggerFactory.getLogger(OrderProcessService.class);
    private final static long SLEEP_DURATION = 5_000;

    @Override
    public void process(Message<Order> message) {
        try {
            Thread.sleep(SLEEP_DURATION);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        logger.debug("Node 1 - Received Message : " + message.getPayload());
    }

}