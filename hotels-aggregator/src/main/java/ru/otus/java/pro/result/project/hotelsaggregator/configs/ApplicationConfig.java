package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.KafkaPropertyConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.ProviderPropertyFileConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.ProviderUserProperty;

@Setter
@Getter
@Configuration
@EnableConfigurationProperties({
        ProviderPropertyFileConfig.class,
        KafkaPropertyConfig.class,
        ProviderUserProperty.class})
public class ApplicationConfig {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .enable(SerializationFeature.INDENT_OUTPUT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .registerModule(new JavaTimeModule())
                .registerModule(new ParameterNamesModule());
    }

}
