package ru.otus.java.pro.result.project.hotels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDto;
import ru.otus.java.pro.result.project.hotels.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderDto;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.Hotel;
import ru.otus.java.pro.result.project.hotels.exceptions.ErrorDto;
import ru.otus.java.pro.result.project.hotels.exceptions.ValidationErrorDto;
import ru.otus.java.pro.result.project.hotels.services.HotelsService;
import ru.otus.java.pro.result.project.hotels.services.UserOrderService;

import java.util.*;

@Tag(name = "Витрина предложений по аренде жилья", description = "Методы поиска и аренды жилья")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/hotels")
public class HotelController {
    private final HotelsService hotelsService;
    private final UserOrderService userOrderService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Метод поиска всех предложений жилья в населенном пункте",
            responses = @ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HotelDto.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)))
    public List<HotelDto> getAllHotels(
            @Parameter(description = "Наименование населенного пункта", required = true, schema = @Schema(type = "string", maxLength = 50, example = "Москва"))
            @NotBlank @Max(50) @RequestParam("city") String city) {
        return hotelsService.findHotels(city).stream().map(HotelDto::mapping).toList();
    }

    @Operation(summary = "Метод поиска жилья с применением фильтров", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = HotelDto.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(description = "Ошибка валидации параметров запроса", responseCode = "422",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = ValidationErrorDto.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @GetMapping(value = "/filter")
    @ResponseStatus(HttpStatus.OK)
    public List<HotelDto> getFilterHotels(
            @Parameter(description = "Набор фильтров с параметрами жилья, по которым ведется поиск", required = true)
            @Valid @ModelAttribute HotelDtoRq hotelDtoRq) {
        List<Hotel> hotels = hotelsService.findFilterHotels(hotelDtoRq);
        return hotels.stream().map(HotelDto::mapping).toList();
    }

    @Operation(summary = "Метод поиска отеля по его идентификатору",
            responses = @ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = HotelDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)))
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HotelDto getHotelByTitle(
            @Parameter(description = "Идентификатор отеля", required = true)
            @Positive @PathVariable("id") int hotelId) {
        return HotelDto.mapping(hotelsService.findHotel(hotelId));
    }

    @Operation(summary = "Метод бронирования жилья", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = UserOrderDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(description = "Ресурс не найден", responseCode = "404",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @PostMapping("/reserve")
    @ResponseStatus(HttpStatus.CREATED)
    public UserOrderDto createOrder(
            @Parameter(description = "Параметры бронирования", required = true)
            @Valid @RequestBody UserOrderCreateDtoRq orderDtoRq) {
        return UserOrderDto.mapping(userOrderService.createUserOrder(orderDtoRq));
    }
}
