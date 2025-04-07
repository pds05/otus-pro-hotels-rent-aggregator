package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoomAmenity;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Schema(description = "Описание отеля")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelDto {
    @Schema(description = "Идентификатор отеля")
    private Integer id;
    @Schema(description = "Название отеля")
    private String title;
    @Schema(description = "Расположение отеля")
    private String address;
    @Schema(description = "Тип отеля")
    private String type;
    @Schema(description = "Звездность отеля", nullable = true)
    private Short grade;
    @Schema(description = "Пользовательский рейтинг отеля", type = "number", example = "9.5")
    private BigDecimal rating;
    @Schema(description = "Список удобств отеля")
    private Map<String, List<String>> amenities = new TreeMap<>();
    @Schema(description = "Список номеров отеля", type = "array")
    private List<HotelRoomDto> rooms;

    @Schema(description = "Описание номера")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomDto{
        @Schema(description = "Идентификатор номера")
        private Integer id;
        @Schema(description = "Название номера")
        private String title;
        @Schema(description = "Площадь номера")
        private Short size;
        @Schema(description = "Количество комнат в номере")
        private Short roomsAmount;
        @Schema(description = "Максимальное число проживающих гостей")
        private Short maxGuests;
        @Schema(description = "Список типов кроватей в номере", type = "array")
        private List<String> beds;
        @Schema(description = "Список удобств номера", type = "array")
        private List<String> amenities;
        @Schema(description = "Список тарифов за номер", type = "array")
        private List<HotelRoomRateDto> rates;
    }

    @Schema(description = "Описание тарифа за номер")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotelRoomRateDto {
        @Schema(description = "Идентификатор тарифа")
        private Integer id;
        @Schema(description = "Название тарифа")
        private String title;
        @Schema(description = "Тип питания")
        private String food;
        @Schema(description = "Возможность возврата оплаты", type = "boolean")
        private Boolean refund;
        @Schema(description = "Стоимость номера", type = "number", example = "4500.00")
        private BigDecimal price;
    }

    private static final Function<Hotel, HotelDto> HOTEL_ENTITY_TO_DTO = h -> new HotelDto(
            h.getId(),
            h.getTitle(),
            h.getCity().getTitle().concat(", ").concat(h.getLocation()),
            h.getHotelType().getTitle(),
            h.getStarGrade(),
            h.getUserRating(),
            h.getHotelsHotelAmenities().stream()
                    .collect(Collectors.groupingBy(amenity -> amenity.getHotelAmenity().getHotelAmenityGroup().getTitle()))
                    .entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey,
                            entry -> entry.getValue().stream().map(amenity -> {
                                        String title = amenity.getIsAdditionalCost() ? amenity.getHotelAmenity().getTitle().concat(" - за дополнительную стоимость") : amenity.getHotelAmenity().getTitle();
                                        if (!amenity.getDescription().isBlank()) {
                                            title = title.concat(" (").concat(amenity.getDescription()).concat(")");
                                        }
                                        return title;
                                    })
                                    .sorted()
                                    .collect(Collectors.toList()))
                    ).entrySet().stream().collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (k, v) -> k, TreeMap::new)),
            h.getHotelRooms().stream().map(room -> new HotelDto.HotelRoomDto(
                    room.getId(),
                    room.getTitle(),
                    room.getSize(),
                    room.getInsideRoomsNumber(),
                    room.getMaxGuests(),
                    room.getHotelRoomBeds().stream().flatMap(bed -> {
                        String[] beds = new String[bed.getAmount()];
                        Arrays.fill(beds, bed.getBedType().getDescription());
                        return Stream.of(beds);
                    }).toList(),
                    room.getHotelRoomAmenities().stream().map(HotelRoomAmenity::getTitle).sorted().toList(),
                    room.getHotelRoomRates().stream().map(rate -> new HotelDto.HotelRoomRateDto(
                            rate.getId(),
                            rate.getTitle(),
                            rate.getFeedType().getDescription(),
                            rate.getIsRefund(),
                            rate.getPrice()
                    )).sorted(Comparator.comparing(HotelDto.HotelRoomRateDto::getPrice)).toList()
            )).toList()
    );

    public static HotelDto mapping(Hotel hotel) {
        return HOTEL_ENTITY_TO_DTO.apply(hotel);
    }

}
