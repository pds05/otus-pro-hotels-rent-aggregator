package ru.otus.java.pro.result.project.messageprocessor.dtos.providers;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class SomeServiceHotelDtoRq extends AbstractProviderDto{

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
