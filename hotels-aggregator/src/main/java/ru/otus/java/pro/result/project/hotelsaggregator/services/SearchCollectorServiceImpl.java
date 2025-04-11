package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.*;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.AggregateHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.ServiceHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.HotelDtoRqMsg;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.HotelsDtoMsg;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.ProviderResponseDto;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.Provider;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ApplicationException;
import ru.otus.java.pro.result.project.hotelsaggregator.messaging.MessageService;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchCollectorServiceImpl implements SearchCollectorService {

    private final MessageService messageService;
    private final List<Provider> providers;
    private final ModelMapper modelMapper;

    @Override
    public AggregateHotelDto searchHotels(HotelDtoRq request) {
        HotelDtoRqMsg providerRq = modelMapper.map(request, HotelDtoRqMsg.class);
        List<ProviderResponseDto<HotelsDtoMsg>> providersResponse = messageService.sendMessage(providerRq, BusinessMethodEnum.FIND_HOTELS_WITH_FILTER);
        return processCollectHotelDto(providersResponse);
    }

    private AggregateHotelDto processCollectHotelDto(List<ProviderResponseDto<HotelsDtoMsg>> providersResponse) {
        List<ServiceHotelDto> preparedResponses = providersResponse.stream().map(response -> {
            Provider provider = getProvider(response.getProviderName());
            HotelsDtoMsg providerHotels = response.getData();
            List<HotelDto> hotels = providerHotels.getHotels().stream().map(dto -> modelMapper.map(dto, HotelDto.class)).collect(Collectors.toList());

            ServiceHotelDto providerDto = new ServiceHotelDto();
            providerDto.setHotels(hotels);
            providerDto.setServiceId(provider.getId());
            providerDto.setService(provider.getTitle());
            return providerDto;
        }).toList();
        return new AggregateHotelDto(preparedResponses);
    }

    private Provider getProvider(String providerName) {
        return providers.stream().filter(provider -> provider.getPropertyName().equalsIgnoreCase(providerName)).findFirst().orElseThrow(() -> new ApplicationException("Provider '" + providerName + "' not found"));
    }
}
