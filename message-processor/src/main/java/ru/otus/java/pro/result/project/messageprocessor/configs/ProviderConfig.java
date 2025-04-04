package ru.otus.java.pro.result.project.messageprocessor.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.pro.result.project.messageprocessor.configs.properties.ProviderPropertyFileConfig;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.exceptions.ApplicationException;
import ru.otus.java.pro.result.project.messageprocessor.services.ProviderService;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProviderConfig {
    private final ProviderService providerService;
    private final ProviderPropertyFileConfig providerPropertyFileConfig;

    @Bean(name = "providers")
    public List<Provider> providers() {
        //работаем только с активными сервисами/провайдерами в базе и в проперти, для которых поле is_active=true в базе и enable=true в проперти
        List<Provider> providers = providerService.getProviders().stream()
                .map(dbProvider -> providerPropertyFileConfig.getProviders().entrySet().stream()
                        .filter(propertyProvider -> propertyProvider.getKey().equalsIgnoreCase(dbProvider.getPropertyName()))
                        .findFirst().map(entry -> {
                                    ProviderPropertyFileConfig.IntegrationServiceProperties fileProperty = entry.getValue();
                                    //так как, по списку провайдеров из проперти динамически создаются бины restController,
                                    //то синхронизируем настройки провайдера в проперти и в базе
                                    //приоритет локальных проперти-настроек
                                    if (fileProperty.getUrl() != null) {
                                        dbProvider.setApiUrl(fileProperty.getUrl());
                                    }
                                    if (fileProperty.getConnectTimeout() != null) {
                                        dbProvider.setConnectTimeout(fileProperty.getConnectTimeout());
                                    }
                                    if (fileProperty.getReadTimeout() != null) {
                                        dbProvider.setReadTimeout(fileProperty.getReadTimeout());
                                    }
                                    if (fileProperty.isEnable() != dbProvider.getIsActive()) {
                                        dbProvider.setIsActive(fileProperty.isEnable());
                                    }
                                    return dbProvider;
                                }
                        ).orElse(dbProvider))
                .filter(provider -> provider.getIsActive().equals(true))
                .toList();
        if (providers.isEmpty()) {
            throw new ApplicationException("No enables providers in configuration");
        }
        log.info("Service providers initiated: {}", providers);
        return providers;
    }
}
