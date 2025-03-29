package ru.otus.java.pro.result.project.messageshifter.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDto {
    private String provider;
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
        private Boolean refund;
        private BigDecimal price;
    }

}
