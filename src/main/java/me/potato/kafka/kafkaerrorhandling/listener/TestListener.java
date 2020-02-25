package me.potato.kafka.kafkaerrorhandling.listener;

import lombok.extern.slf4j.Slf4j;
import me.potato.kafka.kafkaerrorhandling.config.KafkaConfig;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class TestListener {
    @KafkaListener(id = "test-group", topics = KafkaConfig.TOPIC)
    public void on(String in) {
        log.info("Received: in{} ", in);

        if (in.startsWith("foo"))
            throw new RuntimeException("failed");

        log.info("Received: {} ", in);

    }

    @KafkaListener(id = "test-dltgroup", topics = KafkaConfig.DLQTOPIC)
    public void onDeadLetter(String deadLetter) {
        log.info("Received from DeadLetter: {}", deadLetter);
    }
}
