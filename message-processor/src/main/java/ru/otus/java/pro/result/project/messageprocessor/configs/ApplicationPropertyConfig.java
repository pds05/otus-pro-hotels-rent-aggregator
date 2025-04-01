package ru.otus.java.pro.result.project.messageprocessor.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties({ProviderPropertyFileConfig.class})
public class ApplicationPropertyConfig {

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
