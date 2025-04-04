package ru.otus.java.pro.result.project.hotelsaggregator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.CollectHotelDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.HotelDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ErrorDto;
import ru.otus.java.pro.result.project.hotelsaggregator.services.SearchCollectorService;
import ru.otus.java.pro.result.project.hotelsaggregator.services.UserProfileService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/aggregator")
public class GeneralController {
    private final UserProfileService userProfileService;
    private final SearchCollectorService searchCollectorService;

    @GetMapping(value = "/search/filter")
    @ResponseStatus(HttpStatus.OK)
    public CollectHotelDto searchFilterHotels(@ModelAttribute HotelDtoRq hotelDtoRq) {
        return searchCollectorService.searchHotels(hotelDtoRq);
    }

    @GetMapping(value = "/search")
    @ResponseStatus(HttpStatus.OK)
    public CollectHotelDto searchHotels(
            @Parameter(description = "Наименование населенного пункта", required = true, schema = @Schema(type = "string", maxLength = 50, example = "Москва"))
            @NotBlank @RequestParam("city") String city) {
        return searchCollectorService.searchHotelsInCity(city);
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
            @Parameter(description = "Параметры пользователя", required = true)
            @Valid @RequestBody UserDtoRq userDtoRq) {
        return UserDto.mapping(userProfileService.createUserProfile(userDtoRq));
    }

    @Operation(summary = "Запрос профиля пользователя по идентификатору", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "Пользователь не существует", responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
            @ApiResponse(description = "Пользователь отключен", responseCode = "400", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("{id}")
    public UserDto getUserProfile(
            @Parameter(description = "Идентификатор пользователя", required = true)
            @Pattern(regexp = "\\d{8}")
            @PathVariable(name = "id") String userId) {
        return UserDto.mapping(userProfileService.findUserProfile(userId));
    }

    @Operation(summary = "Отключение профиля пользователя", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "Пользователь не существует", responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/delete/{id}")
    public void deleteUserProfile(
            @Parameter(description = "Идентификатор пользователя", required = true)
            @Pattern(regexp = "\\d{8}")
            @PathVariable(name = "id") String userId) {
        userProfileService.deleteUserProfile(userId);
    }

    @Operation(summary = "Возобновление профиля пользователя", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "Пользователь не существует", responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/activate/{id}")
    public UserDto activateUserProfile(
            @Parameter(description = "Идентификатор пользователя", required = true)
            @Pattern(regexp = "\\d{8}")
            @PathVariable(name = "id") String userId) {
        return UserDto.mapping(userProfileService.activateUserProfile(userId));
    }


}
