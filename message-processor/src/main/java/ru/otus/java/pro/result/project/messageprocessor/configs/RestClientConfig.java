package ru.otus.java.pro.result.project.messageprocessor.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

@Configuration
public class RestClientConfig {
    //TODO Регистрация бинов RestClient-ов производится из настроек integration.providers в application.property и не загружает настройки из бызы
    // Регистрация бинов в контексте выполняется до создания бина репозитория для доступа к базе
    @Bean
    public RestClientRegistrator restClientBeans(GenericApplicationContext context, ConfigurableEnvironment environment) {
        return new RestClientRegistrator(context, environment);
    }
}
