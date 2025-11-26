package com.example.kafka_consumer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class SimpleListener {

    @Value("${app.kafka.topic:demo-topic}")
    private String topic;

    @KafkaListener(topics = "${app.kafka.topic:demo-topic}", groupId = "solver-group")
    public void listen(String message) {
        System.out.println("Received message: " + message);
        try {
            String[] parts = message.split(";");
            if (parts.length < 3) {
                System.out.println("Bad message format, expected A;B;C");
                return;
            }

            double A = Double.parseDouble(parts[0]);
            double B = Double.parseDouble(parts[1]);
            double C = Double.parseDouble(parts[2]);

            if (A == 0.0) {
                System.out.println("Error: A == 0, cannot solve");
                return;
            }

            double X = (C - B) / A;
            System.out.printf("Solved: %s * X + %s = %s -> X = %s%n", A, B, C, X);

        } catch (Exception e) {
            System.out.println("Error processing message: " + e.getMessage());
            e.printStackTrace();
        }
    }
}