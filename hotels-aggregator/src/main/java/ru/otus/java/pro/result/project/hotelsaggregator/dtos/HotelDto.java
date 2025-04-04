package ru.otus.java.pro.result.project.hotelsaggregator.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.*;

@ToString
@Schema(description = "Описание отеля")
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class HotelDto {
    @Schema(description = "Название сервиса поставщика")
    private String provider;
    @Schema(description = "Идентификатор отеля")
    private Integer id;
    @Schema(description = "Название отеля")
    private String title;
    @Schema(description = "Расположение отеля")
    private String address;
    @Schema(description = "Тип отеля")
    private String type;
    @Schema(description = "Звездность отеля", nullable = true)
    private Short grade;
    @Schema(description = "Пользовательский рейтинг отеля", type = "number", example = "9.5")
    private BigDecimal rating;
    @Schema(description = "Список удобств отеля")
    private Map<String, List<String>> amenities = new TreeMap<>();
    @Schema(description = "Список номеров отеля", type = "array")
    private List<HotelRoomDto> rooms;

    @Schema(description = "Описание номера")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomDto{
        @Schema(description = "Идентификатор номера")
        private Integer id;
        @Schema(description = "Название номера")
        private String title;
        @Schema(description = "Площадь номера")
        private Short size;
        @Schema(description = "Количество комнат в номере")
        private Short roomsAmount;
        @Schema(description = "Максимальное число проживающих гостей")
        private Short maxGuests;
        @Schema(description = "Список типов кроватей в номере", type = "array")
        private List<String> beds;
        @Schema(description = "Список удобств номера", type = "array")
        private List<String> amenities;
        @Schema(description = "Список тарифов за номер", type = "array")
        private List<HotelRoomRateDto> rates;
    }

    @Schema(description = "Описание тарифа за номер")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomRateDto {
        @Schema(description = "Идентификатор тарифа")
        private Integer id;
        @Schema(description = "Название тарифа")
        private String title;
        @Schema(description = "Тип питания")
        private String food;
        @Schema(description = "Возможность возврата оплаты", type = "boolean")
        private Boolean refund;
        @Schema(description = "Стоимость номера", type = "number", example = "4500.00")
        private BigDecimal price;
    }

}
