package ru.otus.java.pro.result.project.messageshifter.configs;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@RequiredArgsConstructor
@Configuration
public class KafkaConfiguration {

    @Bean
    public NewTopic requestTopic() {
        return TopicBuilder.name("${spring.kafka.producer.request-topic}")
                .partitions(10)
                .replicas(2)
                .build();
    }
}
