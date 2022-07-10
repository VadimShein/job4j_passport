package ru.job4j.passport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class KafkaNotificationService {
    private final PassportService passportService;
    @Autowired
    private KafkaTemplate<Integer, String> kafkaTemplate;

    public KafkaNotificationService(PassportService passportService) {
        this.passportService = passportService;
    }

    private void sendMessage(int msgId, String msg) {
        kafkaTemplate.send("msg", msgId, msg);
    }

    @Scheduled(fixedDelay = 10000)
    private void getUnavailable() {
        passportService.findUnavailable().forEach((p -> sendMessage(p.getId(),
                "passport is unavailable: " + p.toString())));
    }
}
