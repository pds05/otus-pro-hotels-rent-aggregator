package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.pro.result.project.hotelsaggregator.configs.properties.ProviderPropertyFileConfig;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;
import ru.otus.java.pro.result.project.hotelsaggregator.services.ProviderService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class ProviderConfig {

    private final ProviderService providerService;

    private final ProviderPropertyFileConfig providerPropertyFileConfig;

    @Bean(name = "providers")
    public List<Provider> providers() {
        //работаем только с активными сервисами/провайдерами в базе и локальных настройках, и не включенные в список исключения
        //если в локальных настройках нет включенных сервисов, то работаем со всеми активными из базы
        List<Provider> providers = providerService.getProviders().stream()
                .filter(provider -> provider.getIsActive().equals(true))
                .filter(provider -> providerPropertyFileConfig.getEnables().isEmpty() || providerPropertyFileConfig.getEnables().stream().anyMatch(enable -> provider.getPropertyName().equalsIgnoreCase(enable)))
                .filter(provider -> providerPropertyFileConfig.getExclude().stream()
                        .noneMatch(excludeKey -> excludeKey.equalsIgnoreCase(provider.getPropertyName())))
                .collect(Collectors.toList());
        if (providers.isEmpty()) {
            throw new ApplicationException("No enables providers in configuration");
        }
        log.info("Service providers initiated: {}", providers);
        return providers;
    }
}
