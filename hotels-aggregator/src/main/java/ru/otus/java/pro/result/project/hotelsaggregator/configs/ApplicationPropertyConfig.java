package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties({
        ProviderPropertyFileConfig.class,
        KafkaTopicsPropertyConfig.class})
public class ApplicationPropertyConfig {

    @Value("${common.email-domain}")
    private String emailDomain;

}
