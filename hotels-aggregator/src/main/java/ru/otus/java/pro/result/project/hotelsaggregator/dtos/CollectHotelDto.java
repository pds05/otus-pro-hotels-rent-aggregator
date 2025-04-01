package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import lombok.*;

import java.util.List;

@Data
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
