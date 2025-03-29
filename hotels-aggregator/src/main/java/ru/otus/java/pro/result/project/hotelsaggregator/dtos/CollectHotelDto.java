package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CollectHotelDto {

    private List<ProviderHotelDto> providerHotels;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProviderHotelDto {
        private String provider;
        private List<HotelDto> hotels;
    }
}
