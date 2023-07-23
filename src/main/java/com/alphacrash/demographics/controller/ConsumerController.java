package com.alphacrash.demographics.controller;

import com.alphacrash.demographics.entity.Consumer;
import com.alphacrash.demographics.payload.ConsumerDTO;
import com.alphacrash.demographics.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/consumers")
public class ConsumerController {
    
    @Autowired
    private ConsumerService consumerService;
    
    @GetMapping
    public ResponseEntity<List<Consumer>> getAllConsumers() {
        return ResponseEntity.ok(consumerService.getAllConsumers());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Consumer> getConsumerById(@PathVariable Long id) {
        Consumer consumer = consumerService.getConsumerById(id);
        return ResponseEntity.ok(consumer);
    }
    
    @PostMapping
    public ResponseEntity<Consumer> createConsumer(@RequestBody ConsumerDTO consumerDTO) {
        Consumer savedConsumer = consumerService.createConsumer(consumerDTO);
        return new ResponseEntity<>(savedConsumer, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Consumer> updateConsumer(@PathVariable Long id, @RequestBody ConsumerDTO consumerDTO) {
        Consumer updatedConsumer = consumerService.updateConsumer(id, consumerDTO);
        return ResponseEntity.ok(updatedConsumer);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConsumer(@PathVariable Long id) {
        consumerService.deleteConsumer(id);
        return ResponseEntity.noContent().build();
    }
}
