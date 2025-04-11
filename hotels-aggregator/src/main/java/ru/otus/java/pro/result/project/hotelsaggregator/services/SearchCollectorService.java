package ru.otus.java.pro.result.project.hotelsaggregator.services;

import ru.otus.java.pro.result.project.hotelsaggregator.dtos.AggregateHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;

public interface SearchCollectorService {

    AggregateHotelDto searchHotels(HotelDtoRq request);

}
