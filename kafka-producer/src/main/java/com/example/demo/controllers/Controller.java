package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {
    @GetMapping("/app")
    public String home() {
        String instance = System.getenv("INSTANCE");
        if (instance == null || instance.isBlank()) {
            instance = System.getenv("HOSTNAME");
            if (instance == null) instance = "unknown";
        }
        return "Hello from instance=" + instance;
    }
}
