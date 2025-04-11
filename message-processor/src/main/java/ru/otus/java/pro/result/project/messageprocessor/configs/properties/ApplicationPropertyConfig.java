package ru.otus.java.pro.result.project.messageprocessor.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@EnableConfigurationProperties({
        ProviderPropertyFileConfig.class,
        KafkaPropertyConfig.class})
public class ApplicationPropertyConfig {

}
