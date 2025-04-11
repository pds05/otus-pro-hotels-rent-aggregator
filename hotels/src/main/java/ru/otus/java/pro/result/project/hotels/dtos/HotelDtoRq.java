package ru.otus.java.pro.result.project.hotels.dtos;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.*;
import ru.otus.java.pro.result.project.hotels.validators.PriceRangeValid;
import ru.otus.java.pro.result.project.hotels.validators.RequestParametersValid;

import java.util.List;

@Schema(name = "Фильтры отелей", description = "Набор параметров жилья, по которым производится поиск")
@Data
@NoArgsConstructor
@AllArgsConstructor
@PriceRangeValid(min = "priceFrom", max = "priceTo", message = "Wrong price range!")
public class HotelDtoRq {
    @Schema(description = "Наименование населенного пункта", type = "string", requiredMode = Schema.RequiredMode.REQUIRED, maxLength = 50, example = "Москва")
    @NotEmpty
    private String city;
    @Schema(description = "Название отеля", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50, example = "Отель Молодежный")
    private String hotel;
    @Schema(description = "Звездность отеля", type = "integer", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maximum = "5", example = "5")
    @Max(5)
    private Integer stars;
    @Schema(description = "Минимальная стоимость аренды за ночь", type = "integer", requiredMode = Schema.RequiredMode.NOT_REQUIRED, minimum = "0", example = "1000")
    @PositiveOrZero
    private Integer priceFrom;
    @Schema(description = "Максимальная стоимость аренды за ночь", type = "integer", requiredMode = Schema.RequiredMode.NOT_REQUIRED, minimum = "1", example = "5000")
    @Positive
    private Integer priceTo;
    @ArraySchema(schema = @Schema(description = "Тип жилья", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50, example = "hotel"))
    @RequestParametersValid(source = CtHotelType.class)
    List<String> hotelTypes;
    @ArraySchema(schema = @Schema(description = "Удобства отеля", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50, example = "Трансфер"))
    @RequestParametersValid(source = HotelAmenity.class)
    List<String> hotelAmenities;
    @ArraySchema(schema = @Schema(description = "Удобства номера", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50, example = "Кондиционер"))
    @RequestParametersValid(source = HotelRoomAmenity.class)
    List<String> hotelRoomAmenities;
    @ArraySchema(schema = @Schema(description = "Тип кровати в номере", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50, example = "queen"))
    @RequestParametersValid(source = CtHotelBedType.class)
    List<String> beds;
    @ArraySchema(schema = @Schema(description = "Тип питания", type = "string", requiredMode = Schema.RequiredMode.NOT_REQUIRED, maxLength = 50, example = "breakfast"))
    @RequestParametersValid(source = CtHotelFeedType.class)
    List<String> foods;
    @Schema(description = "Количество гостей", type = "integer", requiredMode = Schema.RequiredMode.NOT_REQUIRED, minimum = "1", example = "2")
    @Positive
    private Integer guests;
    @Schema(description = "Количество детей", type = "integer", requiredMode = Schema.RequiredMode.NOT_REQUIRED, minimum = "0", example = "1")
    @PositiveOrZero
    private Integer children;

}
