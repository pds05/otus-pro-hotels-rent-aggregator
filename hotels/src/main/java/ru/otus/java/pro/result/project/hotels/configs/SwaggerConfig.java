package ru.otus.java.pro.result.project.hotels.configs;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;

@Configuration
@OpenAPIDefinition
public class SwaggerConfig {
    @Bean
    public OpenAPI swaggerApiConfig() {
        Info info = new Info()
                .title("Hotels API")
                .description("Описание программных интерфейсов API сервиса аренды жилья Hotels")
                .version("1.0");
        return new OpenAPI().info(info);
    }
}
