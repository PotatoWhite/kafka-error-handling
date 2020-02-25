package me.potato.kafka.kafkaerrorhandling.controllers;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.network.Receive;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TestController {

    public final KafkaTemplate kafkaTemplate;

    public TestController(KafkaTemplate kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @GetMapping("/api/pub")
    public void publish(@RequestParam String message){
        log.info("Receive from remote: {}", message);
        kafkaTemplate.send("testTopic", message);
    }
}
