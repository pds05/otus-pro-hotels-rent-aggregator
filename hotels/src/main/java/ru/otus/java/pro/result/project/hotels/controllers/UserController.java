package ru.otus.java.pro.result.project.hotels.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.otus.java.pro.result.project.hotels.dtos.*;
import ru.otus.java.pro.result.project.hotels.services.UserOrderService;
import ru.otus.java.pro.result.project.hotels.services.UserProfileService;
import ru.otus.java.pro.result.project.hotels.validators.OrderValid;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final UserProfileService userProfileService;
    private final UserOrderService userOrderService;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order")
    public List<UserOrderDto> getUserOrders(
            @Pattern(regexp = "\\d{8}", message = "user-id invalid, must consist of 8 digits")
            @RequestParam("user-id") String userId) {
        return userOrderService.findUserOrders(userId).stream().map(UserOrderDto::mapping).toList();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    public void createUserProfile(@Valid @RequestBody UserDtoRq userDtoRq) {
        userProfileService.createUserProfile(userDtoRq);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/order/cancel")
    public UserOrderDto cancelUserOrder(@OrderValid @RequestParam String order) {
        return UserOrderDto.mapping(userOrderService.canceledUserOrder(order));
    }
}
