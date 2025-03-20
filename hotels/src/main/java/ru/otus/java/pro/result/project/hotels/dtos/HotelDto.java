package ru.otus.java.pro.result.project.hotels.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
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

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class HotelDto {
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
        private String refund;
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
                            rate.getIsRefund().toString(),
                            rate.getPrice()
                    )).sorted(Comparator.comparing(HotelDto.HotelRoomRateDto::getPrice)).toList()
            )).toList()
    );

    public static HotelDto mapping(Hotel hotel) {
        return HOTEL_ENTITY_TO_DTO.apply(hotel);
    }

}
