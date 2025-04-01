package ru.otus.java.pro.result.project.messageprocessor.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "integration")
public class ProviderPropertyFileConfig {
    private BaseProperty base;
    private Map<String, IntegrationServiceProperties> providers;

    @Getter
    @Setter
    public static class IntegrationServiceProperties {
        private String url;
        private Duration readTimeout;
        private Duration connectTimeout;
        private String topicPrefix;
        private boolean enable = true; //default: service enable can be overridden by property
    }

    @Getter
    @Setter
    public static class BaseProperty{
        private String url;
        private Duration readTimeout;
        private Duration connectTimeout;
        private String topicPrefix;
    }

}
