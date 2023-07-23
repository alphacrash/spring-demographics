package com.alphacrash.demographics.service;


import com.alphacrash.demographics.entity.Consumer;
import com.alphacrash.demographics.payload.ConsumerDTO;

import java.util.List;

public interface ConsumerService {

    List<Consumer> getAllConsumers();

    Consumer getConsumerById(Long id);

    Consumer createConsumer(ConsumerDTO consumerDTO);

    Consumer updateConsumer(Long id, ConsumerDTO consumerDTO);

    void deleteConsumer(Long id);
}