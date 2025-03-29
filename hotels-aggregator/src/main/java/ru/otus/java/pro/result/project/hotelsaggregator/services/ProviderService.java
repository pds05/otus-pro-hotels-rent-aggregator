package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.repositories.ProviderRepository;

import java.util.List;

@Getter
@Setter
@Service
@AllArgsConstructor
public class ProviderService {
    private final ProviderRepository providerRepository;

    public List<Provider> getProviders() {
        return providerRepository.findAllProvidersWithApis();
    }
}
