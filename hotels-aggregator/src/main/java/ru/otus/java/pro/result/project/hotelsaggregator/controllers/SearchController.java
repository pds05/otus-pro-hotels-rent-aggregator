package ru.otus.java.pro.result.project.hotelsaggregator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.AggregateHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserOrderDto;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserOrder;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ErrorDto;
import ru.otus.java.pro.result.project.hotelsaggregator.services.SearchCollectorService;
import ru.otus.java.pro.result.project.hotelsaggregator.services.UserOrderService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/offers")
public class SearchController {

    private final SearchCollectorService searchCollectorService;
    private final UserOrderService userOrderService;
    private final ModelMapper modelMapper;

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public AggregateHotelDto searchFilterHotels(@ModelAttribute HotelDtoRq hotelDtoRq) {
        return searchCollectorService.searchHotels(hotelDtoRq);
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
        UserOrder order = userOrderService.createUserOrder(orderDtoRq);
        return modelMapper.map(order, UserOrderDto.class);
    }

}
