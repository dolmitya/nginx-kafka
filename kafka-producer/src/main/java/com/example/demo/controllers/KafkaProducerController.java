package com.example.demo.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class KafkaProducerController {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${app.kafka.topic:demo-topic}")
    private String topic;

    public KafkaProducerController(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/solve")
    public String solve(@RequestParam double A,
                        @RequestParam double B,
                        @RequestParam double C) {

        if (A == 0.0) {
            return "Error: A must be non-zero.";
        }

        String msg = A + ";" + B + ";" + C;
        kafkaTemplate.send(topic, msg);
        return "Sent: " + msg;
    }
}
