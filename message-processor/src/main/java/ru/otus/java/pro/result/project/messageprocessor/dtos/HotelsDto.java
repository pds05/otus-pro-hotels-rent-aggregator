package ru.otus.java.pro.result.project.messageprocessor.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelsDto {

    private List<HotelDto> hotels;
}
