package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.services.ProviderService;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "common")
public class ApplicationConfig {

    @Bean(name = "providers")
    public List<Provider> providers(ProviderService providerService, ProviderPropertyFileConfig providerPropertyFileConfig) {
        return providerService.getProviders().stream().map(dbProvider -> providerPropertyFileConfig.getServices().entrySet().stream()
                .filter(fileService -> fileService.getKey().equalsIgnoreCase(dbProvider.getPropertyName()))
                .findFirst().map(entry -> {
                            ProviderPropertyFileConfig.IntegrationServiceProperties fileProperty = entry.getValue();
                            if (fileProperty.getUrl() != null) {
                                dbProvider.setApiUrl(fileProperty.getUrl());
                            }
                            if (fileProperty.getConnectTimeout() != null) {
                                dbProvider.setConnectTimeout(fileProperty.getConnectTimeout());
                            }
                            if (fileProperty.getReadTimeout() != null) {
                                dbProvider.setReadTimeout(fileProperty.getReadTimeout());
                            }
                            return dbProvider;
                        }
                ).orElse(dbProvider)).toList();
    }

    @Value("${common.email-suffix}")
    private String emailSuffix;

    @Value("${common.api-client-bean-prefix}")
    private String apiClientBeanPrefix;

    private String requestTopicSuffix;

    private String replyTopicSuffix;

    @Value("${spring.kafka.topics-suffix.request}")
    public void setRequestTopicSuffix(String requestTopicSuffix) {
        this.requestTopicSuffix = firstCharacterToUpperCase(requestTopicSuffix);
    }

    @Value("${spring.kafka.topics-suffix.reply}")
    public void setReplyTopicSuffix(String replyTopicSuffix) {
        this.replyTopicSuffix = firstCharacterToUpperCase(replyTopicSuffix);
    }

    private String firstCharacterToUpperCase(String string) {
        return string.substring(0, 1).toUpperCase() + string.substring(1);
    }

}
