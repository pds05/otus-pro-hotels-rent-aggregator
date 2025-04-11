package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotelsaggregator.repositories.ProviderRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProviderServiceImpl implements ProviderService {
    private final ProviderRepository providerRepository;

    public List<Provider> getAllProviders() {
        return providerRepository.findAllProvidersWithApis();
    }

    @Override
    public Provider getProviderById(Integer id) {
        return providerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Provider not found"));
    }
}
