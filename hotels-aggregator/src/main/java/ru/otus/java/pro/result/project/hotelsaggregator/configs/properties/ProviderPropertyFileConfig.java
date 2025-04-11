package ru.otus.java.pro.result.project.hotelsaggregator.configs.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@Getter
@Setter
@ConfigurationProperties(prefix = "integration.providers")
public class ProviderPropertyFileConfig {

    private List<String> enables;

    private List<String> exclude;
}
