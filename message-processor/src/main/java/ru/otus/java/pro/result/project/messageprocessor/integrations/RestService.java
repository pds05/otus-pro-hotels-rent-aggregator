package ru.otus.java.pro.result.project.messageprocessor.integrations;

import ru.otus.java.pro.result.project.messageprocessor.dtos.providers.AbstractProviderDto;
import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;

public interface RestService {

    <T> T get(ProviderApi api, AbstractProviderDto request);

    <T> T post(ProviderApi api, AbstractProviderDto request, Class<T> responseClass);
}
