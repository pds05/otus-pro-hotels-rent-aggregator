package ru.otus.java.pro.result.project.hotelsaggregator.configs;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.services.ProviderService;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Configuration
public class ProviderPropertyConfig {
    private final ProviderService providerService;
    private final ProviderPropertyFileConfig providerPropertyFileConfig;

    @Bean(name = "providers")
    public List<Provider> providers() {
        //работаем только с активными сервисами/провайдерами в базе, списком активных провайдеров в проперти
        //и не включенные в список исключения
        //если enable-список в проперти пустой, то работаем со всеми активными сервисами из базы
        return providerService.getProviders().stream()
                .filter(provider -> provider.getIsActive().equals(true))
                .filter(provider -> providerPropertyFileConfig.getEnables().isEmpty() || providerPropertyFileConfig.getEnables().stream().anyMatch(enable -> provider.getPropertyName().equalsIgnoreCase(enable)))
                .filter(provider -> providerPropertyFileConfig.getExclude().stream()
                        .noneMatch(excludeKey -> excludeKey.equalsIgnoreCase(provider.getPropertyName())))
                .collect(Collectors.toList());
    }
}
