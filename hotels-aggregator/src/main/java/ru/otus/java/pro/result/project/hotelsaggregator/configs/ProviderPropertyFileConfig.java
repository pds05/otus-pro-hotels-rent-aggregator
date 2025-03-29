package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.Duration;
import java.util.Map;

@Getter
@Setter
@ConfigurationProperties(prefix = "integration")
public class ProviderPropertyFileConfig {

    private Map<String, ProviderPropertyFileConfig.IntegrationServiceProperties> services;

    @Getter
    @Setter
    public static class IntegrationServiceProperties {
        private String url;
        private Duration readTimeout;
        private Duration connectTimeout;
    }

}
