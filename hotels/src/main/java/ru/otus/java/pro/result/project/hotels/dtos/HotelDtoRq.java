package ru.otus.java.pro.result.project.hotels.dtos;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.*;
import ru.otus.java.pro.result.project.hotels.validators.PriceRangeValid;
import ru.otus.java.pro.result.project.hotels.validators.RequestParametersValid;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@PriceRangeValid(min = "priceFrom", max = "priceTo", message = "Wrong price range!")
public class HotelDtoRq {
    @NotEmpty
    private String city;

    private String hotel;
    @Max(5)
    private Integer stars;
    @PositiveOrZero
    private Integer priceFrom;
    @PositiveOrZero
    private Integer priceTo;
    @RequestParametersValid(source = CtHotelType.class)
    List<String> hotelTypes;
    @RequestParametersValid(source = HotelAmenity.class)
    List<String> hotelAmenities;
    @RequestParametersValid(source = HotelRoomAmenity.class)
    List<String> hotelRoomAmenities;
    @RequestParametersValid(source = CtHotelBedType.class)
    List<String> beds;
    @RequestParametersValid(source = CtHotelFeedType.class)
    List<String> foods;

    private Integer guests;

    private Integer children;

}
