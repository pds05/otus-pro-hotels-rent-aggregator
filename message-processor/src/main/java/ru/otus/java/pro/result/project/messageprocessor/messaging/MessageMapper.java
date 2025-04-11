package ru.otus.java.pro.result.project.messageprocessor.messaging;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.result.project.messageprocessor.dtos.messages.AbstractMessageDto;
import ru.otus.java.pro.result.project.messageprocessor.dtos.providers.AbstractProviderDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MessageMapper {

    private final ModelMapper modelMapper;

    public <T extends AbstractProviderDto> T toProviderDto(AbstractMessageDto internalDto, Class<T> targetClass) {
        return internalDto == null ? null : modelMapper.map(internalDto, targetClass);
    }

    public <T extends AbstractMessageDto> T toInternalDto(Object providerDto, Class<T> targetClass) {
        return providerDto == null ? null : modelMapper.map(providerDto, targetClass);
    }

    public <T extends AbstractProviderDto> List<T> toProviderDto(List<AbstractMessageDto> internalDtos, Class<T> targetClass) {
        return internalDtos == null ? null : internalDtos.stream().map(input -> toProviderDto(input, targetClass)).toList();
    }

    public <T extends AbstractMessageDto, V> List<T> toInternalDto(List<V> providerDtos, Class<T> targetClass) {
        return providerDtos == null ? null : providerDtos.stream().map(input -> toInternalDto(input, targetClass)).toList();
    }

}
