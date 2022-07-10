package ru.job4j.passport.controller;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaPassportController {
    @KafkaListener(topics = "msg")
    public void msgListener(String msg) {
        System.out.println(msg);
    }
}
