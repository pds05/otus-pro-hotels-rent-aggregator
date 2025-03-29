package ru.otus.java.pro.result.project.messageshifter.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDtoRq {
    private String city;
    private String hotel;
    private Integer stars;
    private Integer priceFrom;
    private Integer priceTo;
    List<String> hotelTypes;
    List<String> hotelAmenities;
    List<String> hotelRoomAmenities;
    List<String> beds;
    List<String> foods;
    private Integer guests;
    private Integer children;

}
