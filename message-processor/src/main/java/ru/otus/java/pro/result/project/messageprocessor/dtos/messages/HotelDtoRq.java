package ru.otus.java.pro.result.project.messageprocessor.dtos.messages;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class HotelDtoRq extends InternalDto {
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
