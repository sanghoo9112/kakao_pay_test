package com.derekpark.balance.service;

import com.derekpark.balance.model.Recipient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageProducer {

    private final static String EXCHANGE_NAME = "direct";
    private RabbitTemplate rabbitTemplate;


    @Autowired
    MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    public void produceRecipient(String routingKEy, Recipient recipient) {
        rabbitTemplate.convertAndSend(EXCHANGE_NAME, routingKEy, recipient);
    }

}