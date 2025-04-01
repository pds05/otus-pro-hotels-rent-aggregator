package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelDtos {

    private List<HotelDto> hotels;
}
