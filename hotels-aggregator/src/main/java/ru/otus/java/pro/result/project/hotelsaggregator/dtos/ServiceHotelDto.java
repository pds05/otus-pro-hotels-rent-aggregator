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
public class ServiceHotelDto {

    private String service;

    private Integer serviceId;

    private List<HotelDto> hotels;
}
