package ru.otus.java.pro.result.project.messageprocessor.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.messageprocessor.entities.Provider;
import ru.otus.java.pro.result.project.messageprocessor.repositories.ProviderRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProviderService {

    private final ProviderRepository providerRepository;

    public List<Provider> getProviders() {
        return providerRepository.findAllProvidersWithApis();
    }
}
