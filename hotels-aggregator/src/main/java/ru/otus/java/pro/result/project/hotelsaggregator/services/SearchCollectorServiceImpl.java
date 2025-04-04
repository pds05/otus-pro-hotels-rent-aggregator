package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.*;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.CollectHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.ProviderHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.ProviderResponseDto;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;
import ru.otus.java.pro.result.project.hotelsaggregator.messaging.MessageService;
import ru.otus.java.pro.result.project.hotelsaggregator.utils.ApplicationUtil;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class SearchCollectorServiceImpl implements SearchCollectorService {
    private final MessageService messageService;

    @Override
    public CollectHotelDto searchHotels(HotelDtoRq request) {
        List<ProviderResponseDto<List<HotelDto>>> responseList = messageService.sendMessage(request, BusinessMethodEnum.FIND_HOTELS_WITH_FILTER, ProviderResponseDto.class);
        return processCollectHotelDto(responseList);
    }

    private CollectHotelDto processCollectHotelDto(List<ProviderResponseDto<List<HotelDto>>> responseList) {
        List<ProviderHotelDto> preparedResponses = responseList.stream().map(response -> {
            List<HotelDto> hotels = response.getData();
            ProviderHotelDto providerDto = new ProviderHotelDto();
            providerDto.setHotels(hotels);
            providerDto.setService(ApplicationUtil.getProvider(response.getProviderName()).getTitle());
            return providerDto;
        }).toList();
        return new CollectHotelDto(preparedResponses);
    }

    @Override
    public CollectHotelDto searchHotelsInCity(String city) {
        HotelDtoRq req = new HotelDtoRq();
        req.setCity(city);
        List<ProviderResponseDto<List<HotelDto>>> responseList = messageService.sendMessage(req, BusinessMethodEnum.FIND_ALL_HOTELS_IN_CITY, ProviderResponseDto.class);
        return processCollectHotelDto(responseList);
    }
}
