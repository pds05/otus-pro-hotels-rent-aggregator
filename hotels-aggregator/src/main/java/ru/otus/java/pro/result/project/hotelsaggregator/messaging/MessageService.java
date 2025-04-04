package ru.otus.java.pro.result.project.hotelsaggregator.messaging;

import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.ProviderResponseDto;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;

import java.util.List;

public interface MessageService {

    <T> List<ProviderResponseDto<T>> sendMessage(Object message, BusinessMethodEnum method, Class<?> clazz);

    <T> ProviderResponseDto<T> sendMessage(Provider provider, Object message, BusinessMethodEnum method, Class<?> clazz);
}
