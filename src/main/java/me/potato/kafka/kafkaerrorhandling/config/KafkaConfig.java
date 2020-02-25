package me.potato.kafka.kafkaerrorhandling.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.ConcurrentKafkaListenerContainerFactoryConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.SeekToCurrentErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Slf4j
@Configuration
public class KafkaConfig {
    public static final String TOPIC = "testTopic";
    public static final String DLQTOPIC = "testTopic.DLT";

    @Bean
    public NewTopic createTestTopic() {
        return new NewTopic(TOPIC, 1, (short) 1);
    }

    @Bean
    public NewTopic createTestTopic_DeadLetter() {
        return new NewTopic(DLQTOPIC, 1, (short) 1);
    }


    @Bean
    public ConcurrentKafkaListenerContainerFactory kafkaListenerContainerFactory(
            ConcurrentKafkaListenerContainerFactoryConfigurer configurer,
            ConsumerFactory consumerFactory,
            KafkaTemplate kafkaTemplate) {

        ConcurrentKafkaListenerContainerFactory<Object, Object> factory = new ConcurrentKafkaListenerContainerFactory<>();
        configurer.configure(factory, consumerFactory);
        factory.setErrorHandler(new SeekToCurrentErrorHandler(new DeadLetterPublishingRecoverer(kafkaTemplate), new FixedBackOff(0L, 2)));
        return factory;
    }

}
