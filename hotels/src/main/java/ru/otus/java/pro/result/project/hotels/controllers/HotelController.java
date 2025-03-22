package ru.otus.java.pro.result.project.hotels.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDto;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderDto;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.services.HotelsService;
import ru.otus.java.pro.result.project.hotels.services.UserOrderService;

import java.util.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private final HotelsService hotelsService;
    private final UserOrderService userOrderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<HotelDto> getAllHotels(@NotBlank @RequestParam("city") String city) {
        return hotelsService.findHotels(city).stream().map(HotelDto::mapping).toList();
    }

    @GetMapping(value = "/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelDto> getFilterHotels(@ModelAttribute HotelDtoRq hotelDtoRq) {
        List<Hotel> hotels = hotelsService.findFilterHotels(hotelDtoRq);
        return hotels.stream().map(HotelDto::mapping).toList();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelDto getHotelByTitle(
            @Positive @PathVariable("id") int hotelId,
            @NotBlank @RequestParam("city") String cityTitle) {
        return HotelDto.mapping(hotelsService.findHotel(hotelId, cityTitle).orElseThrow(() -> new ResourceNotFoundException("Hotel not exist")));
    }

    @PostMapping("/reserve")
    public UserOrderDto createOrder(@Valid @RequestBody UserOrderCreateDtoRq orderDtoRq) {
        return UserOrderDto.mapping(userOrderService.createUserOrder(orderDtoRq));
    }
}
