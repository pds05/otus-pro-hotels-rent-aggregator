package ru.otus.java.pro.result.project.messageprocessor.integrations;

import ru.otus.java.pro.result.project.messageprocessor.entities.ProviderApi;

public interface RestService {

    <T> T getRequest(ProviderApi api, Object request);

    <T> T postRequest(ProviderApi api, Class<T> dtoClass);
}
