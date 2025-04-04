package ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDto;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProviderHotelDto {

    private String service;

    private List<HotelDto> hotels;
}
