package ru.otus.java.pro.result.project.hotelsaggregator.dtos.internals;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectHotelDto {

    private List<ProviderHotelDto> services = new ArrayList<>();

    public void addHotel(ProviderHotelDto providerHotelDto) {
        services.add(providerHotelDto);
    }

    public void removeHotel(ProviderHotelDto providerHotelDto) {
        services.remove(providerHotelDto);
    }

}
