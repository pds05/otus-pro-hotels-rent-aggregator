package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {

    @Bean
    public OpenAPI swaggerApiConfig() {
        Info info = new Info()
                .title("Hotels Rent Aggregator API")
                .description("Описание программных интерфейсов API поисковика аренды жилья")
                .version("1.0");
        return new OpenAPI().info(info);
    }
}
