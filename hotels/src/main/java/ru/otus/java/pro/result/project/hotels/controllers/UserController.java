package ru.otus.java.pro.result.project.hotels.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotels.dtos.*;
import ru.otus.java.pro.result.project.hotels.exceptions.ErrorDto;
import ru.otus.java.pro.result.project.hotels.services.UserOrderService;
import ru.otus.java.pro.result.project.hotels.services.UserProfileService;
import ru.otus.java.pro.result.project.hotels.validators.OrderValid;

import java.util.List;

@Tag(name = "Личный кабинет пользователя", description = "Методы управления профилем и заказами")
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserProfileService userProfileService;
    private final UserOrderService userOrderService;

    @Operation(summary = "Получить все бронирования пользователя",
            responses = @ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(array = @ArraySchema(schema = @Schema(implementation = UserOrderDto.class)),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order")
    public List<UserOrderDto> getUserOrders(
            @Parameter(description = "Идентификатор пользователя", required = true, example = "00000001")
            @Pattern(regexp = "\\d{8}", message = "user-id invalid, must consist of 8 digits")
            @RequestParam("user-id") String userId) {
        return userOrderService.findUserOrders(userId).stream().map(UserOrderDto::mapping).toList();
    }

    @Operation(summary = "Получить данные по бронированию",
            responses = @ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UserOrderDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)))
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order/{id}")
    public UserOrderDto getUserOrder(
            @Parameter(description = "Номер бронирования", required = true, example = "00000001-1")
            @OrderValid
            @PathVariable(name = "id") String order) {
        return UserOrderDto.mapping(userOrderService.findUserOrder(order));
    }

    @Operation(summary = "Метод регистрации нового пользователя", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = UserDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(description = "Пользователь уже существует", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public UserDto createUserProfile(
            @Parameter(description = "Данные пользователя", required = true)
            @Valid @RequestBody UserDtoRq userDtoRq) {
        return UserDto.mapping(userProfileService.createUserProfile(userDtoRq));
    }

    @Operation(summary = "Метод отмены бронирования", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200",
                    content = @Content(schema = @Schema(implementation = UserOrderDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(description = "Бронирование не подлежит отмене", responseCode = "400",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order/cancel")
    public UserOrderDto cancelUserOrder(
            @Parameter(description = "Номер бронирования", required = true, example = "00000001-1")
            @OrderValid @RequestParam String order) {
        return UserOrderDto.mapping(userOrderService.canceledUserOrder(order));
    }
}
