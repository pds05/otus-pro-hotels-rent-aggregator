package ru.otus.java.pro.result.project.hotelsaggregator.services;

import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;

import java.util.List;

public interface ProviderService {

    List<Provider> getAllProviders();

    Provider getProviderById(Integer id);
}
