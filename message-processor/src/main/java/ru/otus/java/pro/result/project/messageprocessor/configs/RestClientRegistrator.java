package ru.otus.java.pro.result.project.messageprocessor.configs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import ru.otus.java.pro.result.project.messageprocessor.configs.properties.ProviderPropertyFileConfig;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;

import java.util.Optional;


@Slf4j
public class RestClientRegistrator {
    public static final String REST_CLIENT_BEAN_SUFFIX = "RestClient";

    public RestClientRegistrator(GenericApplicationContext context, Environment environment) {
        Binder binder = Binder.get(environment);
        ProviderPropertyFileConfig properties = binder.bind("integration", Bindable.of(ProviderPropertyFileConfig.class)).get();
        properties.getProviders().entrySet().stream()
                .filter(propertyProvider -> propertyProvider.getValue().isEnable())
                .map(propertyProvider ->
                        Provider.builder().propertyName(propertyProvider.getKey())
                                .apiUrl(propertyProvider.getValue().getUrl())
                                .readTimeout(Optional.ofNullable(propertyProvider.getValue().getReadTimeout())
                                        .orElse(properties.getBase().getReadTimeout()))
                                .connectTimeout(Optional.ofNullable(propertyProvider.getValue().getConnectTimeout())
                                        .orElse(properties.getBase().getConnectTimeout()))
                                .build())
                .forEach(provider -> {
                    String beanName = provider.getPropertyName().concat(REST_CLIENT_BEAN_SUFFIX);
                    context.registerBean(beanName, RestClient.class,
                            () -> {
                                HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
                                factory.setConnectTimeout(provider.getConnectTimeout());
                                factory.setReadTimeout(provider.getReadTimeout());
                                return RestClient.builder().requestFactory(factory).baseUrl(provider.getApiUrl()).build();
                            });
                    log.info("Rest client '{}' launched", beanName);
                });
    }
}
