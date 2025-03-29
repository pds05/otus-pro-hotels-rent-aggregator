package ru.otus.java.pro.result.project.hotelsaggregator.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.CollectHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.BusinessMethodEnum;

import java.util.Collections;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SearchCollectorServiceImpl implements SearchCollectorService {
    private final KafkaProducerService kafkaProducerService;

    @Override
    public CollectHotelDto searchHotels(HotelDtoRq request) {
        List<HotelDto> response = kafkaProducerService.sendMessage(request, BusinessMethodEnum.FIND_HOTELS_WITH_FILTER);
        CollectHotelDto collectHotelDto = new CollectHotelDto();
        collectHotelDto.setProviderHotels(Collections.singletonList(new CollectHotelDto.ProviderHotelDto("test service", response)));
        return collectHotelDto;
    }
}
