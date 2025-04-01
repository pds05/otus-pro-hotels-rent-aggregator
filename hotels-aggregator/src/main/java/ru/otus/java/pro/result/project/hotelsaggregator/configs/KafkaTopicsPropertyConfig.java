package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "spring.kafka.topics")
public class KafkaTopicsPropertyConfig {

    private Integer partitionsCount;

    private Integer replicasCount;

    private String requestTopicSuffix;

    @Value("${spring.kafka.topics.name-suffixes.request-topic}")
    public void setRequestTopicSuffix(String requestTopicSuffix) {
        this.requestTopicSuffix = firstCharacterToUpperCase(requestTopicSuffix);
    }

    private String replyTopicSuffix;

    @Value("${spring.kafka.topics.name-suffixes.reply-topic}")
    public void setReplyTopicSuffix(String replyTopicSuffix) {
        this.replyTopicSuffix = firstCharacterToUpperCase(replyTopicSuffix);
    }

    private String firstCharacterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
