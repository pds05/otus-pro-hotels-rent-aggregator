package ru.otus.java.pro.result.project.hotelsaggregator.services;

import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.CollectHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;

public interface SearchCollectorService {

    CollectHotelDto searchHotels(HotelDtoRq request);

    CollectHotelDto searchHotelsInCity(String city);
}
