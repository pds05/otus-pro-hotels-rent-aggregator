package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.*;
import ru.otus.java.pro.result.project.hotelsaggregator.enums.BusinessMethodEnum;

import java.util.Collections;
@RequiredArgsConstructor
@Service
public class SearchCollectorServiceImpl implements SearchCollectorService {
    private final KafkaProducerService kafkaProducerService;

    @Override
    public CollectHotelDto searchHotels(HotelDtoRq request) {
        HotelDtos hotelDtos = kafkaProducerService.sendMessage(request, BusinessMethodEnum.FIND_HOTELS_WITH_FILTER, HotelDtos.class);
        CollectHotelDto collectHotelDto = new CollectHotelDto();
        collectHotelDto.setProviderHotels(Collections.singletonList(new CollectHotelDto.ProviderHotelDto("Nif-Nif Hotel", hotelDtos.getHotels())));
        return collectHotelDto;
    }

    @Override
    public CollectHotelDto searchHotelsInCity(String city) {
        HotelDtoRq req = new HotelDtoRq();
        req.setCity(city);
        HotelDtos hotelDtos = kafkaProducerService.sendMessage(req, BusinessMethodEnum.FIND_ALL_HOTELS_IN_CITY, HotelDtos.class);
        CollectHotelDto collectHotelDto = new CollectHotelDto();
        collectHotelDto.setProviderHotels(Collections.singletonList(new CollectHotelDto.ProviderHotelDto("Nif-Nif Hotel", hotelDtos.getHotels())));
        return collectHotelDto;
    }
}
