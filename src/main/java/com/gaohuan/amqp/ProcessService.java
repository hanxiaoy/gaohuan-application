package com.gaohuan.amqp;

import org.springframework.messaging.Message;


public interface ProcessService<T> {

    /**
     * Processes incoming message(s)
     *
     * @param message SI Message.
     */
    void process(Message<T> message);

}