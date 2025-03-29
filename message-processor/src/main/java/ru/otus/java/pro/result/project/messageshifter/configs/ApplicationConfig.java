package ru.otus.java.pro.result.project.messageshifter.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Map;

@Getter
@Setter
@Component
public class ApplicationConfig {

    @Value("${common.email-suffix}")
    private String emailSuffix;
    @Value("${common.api-client-bean-prefix}")
    private String apiClientBeanPrefix;

    private String requestTopicSuffix;

    private String replyTopicSuffix;

    private Map<String, IntegrationServiceProperties> integrationServices;

    @ConfigurationProperties(prefix = "integration")
    public void setIntegrationServices(Map<String, IntegrationServiceProperties> integrationServices) {
        this.integrationServices = integrationServices;
    }

    @Value("${spring.kafka.topics-suffix.request}")
    public void setRequestTopicSuffix(String requestTopicSuffix) {
        this.requestTopicSuffix = firstCharacterToUpperCase(requestTopicSuffix);
    }

    @Value("${spring.kafka.topics-suffix.reply}")
    public void setReplyTopicSuffix(String replyTopicSuffix) {
        this.replyTopicSuffix = firstCharacterToUpperCase(replyTopicSuffix);
    }

    @Getter
    @Setter
    public static class IntegrationServiceProperties {
        private String url;
        private Duration readTimeout;
        private Duration connectTimeout;
    }

    public String firstCharacterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
