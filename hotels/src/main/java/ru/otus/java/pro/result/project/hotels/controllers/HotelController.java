package ru.otus.java.pro.result.project.hotels.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDto;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.entities.HotelRoomAmenity;
import ru.otus.java.pro.result.project.hotels.services.HotelsService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private final HotelsService hotelsService;

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
                    room.getHotelRoomsRates().stream().map(rate -> new HotelDto.HotelRoomRateDto(
                            rate.getId(),
                            rate.getTitle(),
                            rate.getFeedType().getDescription(),
                            rate.getIsRefund().toString(),
                            rate.getPrice()
                    )).sorted(Comparator.comparing(HotelDto.HotelRoomRateDto::getPrice)).toList()
            )).toList()
    );

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HotelDto> getAllHotels(
            @RequestParam(value = "city") String city
    ) {
        return hotelsService.findHotels(city).stream().map(HOTEL_ENTITY_TO_DTO).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelDto getHotelByTitle(
            @PathVariable(value = "id") int hotelId,
            @RequestParam(value = "city") String cityTitle) {
        return HOTEL_ENTITY_TO_DTO.apply(hotelsService.findHotel(hotelId, cityTitle).orElseThrow());
    }
}
