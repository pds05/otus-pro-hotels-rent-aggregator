package ru.otus.java.pro.result.project.messageprocessor.messaging;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.result.project.messageprocessor.dtos.messages.InternalDto;

import java.util.List;

@RequiredArgsConstructor
@Component
public class MessageMapper {

    private final ModelMapper modelMapper;

    public <T> T toProviderDto(InternalDto internalDto, Class<T> targetClass) {
        return internalDto == null ? null : modelMapper.map(internalDto, targetClass);
    }

    public <T extends InternalDto> T toInternalDto(Object providerDto, Class<T> targetClass) {
        return providerDto == null ? null : modelMapper.map(providerDto, targetClass);
    }

    public <T> List<T> toProviderDto(List<InternalDto> internalDtos, Class<T> targetClass) {
        return internalDtos == null ? null : internalDtos.stream().map(input -> toProviderDto(input, targetClass)).toList();
    }

    public <T extends InternalDto, V> List<T> toInternalDto(List<V> providerDtos, Class<T> targetClass) {
        return providerDtos == null ? null : providerDtos.stream().map(input -> toInternalDto(input, targetClass)).toList();
    }

}
