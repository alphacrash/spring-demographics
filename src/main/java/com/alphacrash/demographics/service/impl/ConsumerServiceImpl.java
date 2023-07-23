package com.alphacrash.demographics.service.impl;

import com.alphacrash.demographics.entity.Consumer;
import com.alphacrash.demographics.exception.ResourceNotFoundException;
import com.alphacrash.demographics.payload.ConsumerDTO;
import com.alphacrash.demographics.repository.ConsumerRepository;
import com.alphacrash.demographics.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ConsumerRepository consumerRepository;

    @Override
    public List<Consumer> getAllConsumers() {
        return consumerRepository.findAll();
    }

    @Override
    public Consumer getConsumerById(Long id) {
        return consumerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer", "id", id));
    }

    @Override
    public Consumer createConsumer(ConsumerDTO consumer) {
        Consumer newConsumer = mapConsumerDTOToConsumer(consumer);
        return consumerRepository.save(newConsumer);
    }

    private Consumer mapConsumerDTOToConsumer(ConsumerDTO consumer) {
        Consumer newConsumer = new Consumer();
        newConsumer.setFirstName(consumer.getFirstName());
        newConsumer.setLastName(consumer.getLastName());
        newConsumer.setEmail(consumer.getEmail());
        return newConsumer;
    }

    @Override
    public Consumer updateConsumer(Long id, ConsumerDTO consumer) {
        Consumer existingConsumer = consumerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Consumer", "id", id));
        existingConsumer.setFirstName(consumer.getFirstName());
        existingConsumer.setLastName(consumer.getLastName());
        existingConsumer.setEmail(consumer.getEmail());
        return consumerRepository.save(existingConsumer);
    }

    @Override
    public void deleteConsumer(Long id) {
        consumerRepository.deleteById(id);
    }
}