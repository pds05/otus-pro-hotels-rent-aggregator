package ru.otus.java.pro.result.project.hotels.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    private Integer id;
    private String title;
    private String address;
    private String type;
    private Short grade;
    private BigDecimal rating;
    private Map<String, List<String>> amenities = new TreeMap<>();
    private List<HotelRoomDto> rooms;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomDto{
        private Integer id;
        private String title;
        private Short size;
        private Short roomsAmount;
        private Short maxGuests;
        private List<String> beds;
        private List<String> amenities;
        private List<HotelRoomRateDto> rates;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomRateDto {
        private Integer id;
        private String title;
        private String food;
        private String refund;
        private BigDecimal price;
    }

}
