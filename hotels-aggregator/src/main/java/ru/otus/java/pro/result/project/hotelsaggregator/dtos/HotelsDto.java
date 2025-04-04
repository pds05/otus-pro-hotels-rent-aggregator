package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals.ProviderResponseDto;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
public class HotelsDto extends ProviderResponseDto {
    private List<HotelDto> hotels;
}
