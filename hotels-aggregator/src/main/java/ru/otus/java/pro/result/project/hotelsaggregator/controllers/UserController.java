package ru.otus.java.pro.result.project.hotelsaggregator.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDto;
import ru.otus.java.pro.result.project.hotelsaggregator.dtos.UserDtoRq;
import ru.otus.java.pro.result.project.hotelsaggregator.entities.UserProfile;
import ru.otus.java.pro.result.project.hotelsaggregator.exceptions.ErrorDto;
import ru.otus.java.pro.result.project.hotelsaggregator.services.UserProfileService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    public static final String USER_ID_PATTERN = "\\d{10}";

    private final UserProfileService userProfileService;
    private final ModelMapper modelMapper;

    @Operation(summary = "Метод регистрации нового пользователя", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "201",
                    content = @Content(schema = @Schema(implementation = UserDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE)),
            @ApiResponse(description = "Пользователь уже существует", responseCode = "422",
                    content = @Content(schema = @Schema(implementation = ErrorDto.class),
                            mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public UserDto createUserProfile(
            @Parameter(description = "Параметры пользователя", required = true)
            @Valid @RequestBody UserDtoRq userDtoRq) {
        UserProfile user = userProfileService.createUserProfile(userDtoRq);
        return modelMapper.map(user, UserDto.class);
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
            @Pattern(regexp = USER_ID_PATTERN)
            @PathVariable(name = "id") String userId) {
        UserProfile user = userProfileService.findUserProfile(userId);
        return modelMapper.map(user, UserDto.class);
    }

    @Operation(summary = "Отключение профиля пользователя", responses = {
            @ApiResponse(description = "Успешный ответ", responseCode = "200", content = @Content(schema = @Schema(implementation = UserDto.class))),
            @ApiResponse(description = "Пользователь не существует", responseCode = "404", content = @Content(schema = @Schema(implementation = ErrorDto.class))),
    })
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/delete/{id}")
    public void deleteUserProfile(
            @Parameter(description = "Идентификатор пользователя", required = true)
            @Pattern(regexp = USER_ID_PATTERN)
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
            @Pattern(regexp = USER_ID_PATTERN)
            @PathVariable(name = "id") String userId) {
        UserProfile user = userProfileService.activateUserProfile(userId);
        return modelMapper.map(user, UserDto.class);
    }
}
