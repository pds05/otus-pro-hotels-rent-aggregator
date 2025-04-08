package ru.otus.java.pro.result.project.hotels.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Schema(description = "Список предложений с отелями")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelsDto {
    @Schema(description = "Отели")
    private List<HotelDto> hotels;

}
