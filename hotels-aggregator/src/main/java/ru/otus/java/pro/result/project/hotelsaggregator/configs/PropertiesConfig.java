package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties({ApplicationConfig.class, ProviderPropertyFileConfig.class})
public class PropertiesConfig {

}
