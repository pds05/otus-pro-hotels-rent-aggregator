package ru.otus.java.pro.result.project.messageprocessor.dtos.messages;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDto extends InternalDto{
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
