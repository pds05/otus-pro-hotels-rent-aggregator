package ru.otus.java.pro.result.project.hotels.validators;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.exceptions.CustomValidationException;
import ru.otus.java.pro.result.project.hotels.exceptions.ValidationFieldError;
import ru.otus.java.pro.result.project.hotels.services.HotelsServiceImpl;

@RequiredArgsConstructor
@Component
public class HotelValidator {
    private static HotelsServiceImpl hotelsService;

    public static void validate(int id) {
        hotelsService.findHotel(id).orElseThrow(() -> {
            ValidationFieldError error = new ValidationFieldError(UserOrderCreateDtoRq.HOTEL_ID_FIELD, "Hotel id not found");
            CustomValidationException exception = new CustomValidationException("HOTEL_NO_EXIST", "Hotel not exist");
            exception.addError(error);
            return exception;
        });
    }

    @Autowired
    public void setHotelsService(HotelsServiceImpl hotelsService) {
        HotelValidator.hotelsService = hotelsService;
    }
}
