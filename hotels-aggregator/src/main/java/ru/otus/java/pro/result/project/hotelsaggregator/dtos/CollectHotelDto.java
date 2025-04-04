package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import lombok.*;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages.ProviderHotelDto;

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
