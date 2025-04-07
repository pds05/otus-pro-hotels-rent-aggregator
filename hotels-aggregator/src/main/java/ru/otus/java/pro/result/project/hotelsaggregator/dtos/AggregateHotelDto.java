package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AggregateHotelDto {

    private List<ServiceHotelDto> services = new ArrayList<>();

    public void addHotel(ServiceHotelDto serviceHotelDto) {
        services.add(serviceHotelDto);
    }

    public void removeHotel(ServiceHotelDto serviceHotelDto) {
        services.remove(serviceHotelDto);
    }

}
