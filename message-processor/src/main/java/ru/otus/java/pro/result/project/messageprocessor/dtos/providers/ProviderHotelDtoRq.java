package ru.otus.java.pro.result.project.messageprocessor.dtos.providers;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProviderHotelDtoRq {
    private String city;
    private String hotel;
    private Integer stars;
    private Integer priceFrom;
    private Integer priceTo;
    @JsonIgnore
    List<String> hotelTypes;
    @JsonIgnore
    List<String> hotelAmenities;
    @JsonIgnore
    List<String> hotelRoomAmenities;
    @JsonIgnore
    List<String> beds;
    @JsonIgnore
    List<String> foods;
    private Integer guests;
    private Integer children;

}
