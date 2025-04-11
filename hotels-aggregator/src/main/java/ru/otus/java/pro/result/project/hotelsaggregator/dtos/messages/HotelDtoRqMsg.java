package ru.otus.java.pro.result.project.hotelsaggregator.dtos.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDtoRqMsg {

    private String city;
    private String hotel;
    private Integer stars;
    private Integer priceFrom;
    private Integer priceTo;
    private Integer guests;
    private Integer children;
    List<String> hotelTypes;
    List<String> hotelAmenities;
    List<String> hotelRoomAmenities;
    List<String> beds;
    List<String> foods;

}
