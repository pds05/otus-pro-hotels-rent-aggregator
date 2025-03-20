package ru.otus.java.pro.result.project.hotels.validators;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import ru.otus.java.pro.result.project.hotels.dtos.UserOrderCreateDtoRq;
import ru.otus.java.pro.result.project.hotels.entities.UserOrderKey;
import ru.otus.java.pro.result.project.hotels.exceptions.ResourceNotFoundException;
import ru.otus.java.pro.result.project.hotels.exceptions.CustomValidationException;
import ru.otus.java.pro.result.project.hotels.exceptions.ValidationFieldError;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@Component
public class UserOrderValidator {

    public void validate(UserOrderCreateDtoRq request) throws CustomValidationException {
        List<ValidationFieldError> errors = new ArrayList<>();
        if (request.getUserId().length() != 8) {
            errors.add(new ValidationFieldError(UserOrderCreateDtoRq.USER_ID_FIELD, "Field must contain 8 characters"));
        }
        if (request.getDateIn().isBefore(LocalDate.now())) {
            errors.add(new ValidationFieldError(UserOrderCreateDtoRq.DATE_IN_FIELD, "Data must be after current date"));
        }
        if (request.getDateOut().isBefore(LocalDate.now())) {
            errors.add(new ValidationFieldError(UserOrderCreateDtoRq.DATE_IN_FIELD, "Data must be after current date"));
        }
        if(request.getDateIn().isAfter(request.getDateOut())) {
            errors.add(new ValidationFieldError(UserOrderCreateDtoRq.DATE_IN_FIELD, "DATA_FROM must be before DATA_TO"));
        }
        if(!errors.isEmpty()) {
            throw new CustomValidationException("ROOM_RESERVE_REQUEST_ERROR", "Request with parameters error", errors);
        }
    }

    public UserOrderKey parseAndValidateOrder(String compoundOrder) {
        List<ValidationFieldError> errors = new ArrayList<>();
        if(compoundOrder == null || compoundOrder.isEmpty()) {

        }
        try {
            int userOrderId = Integer.parseInt(compoundOrder.split("-")[0]);
            String userProfileId = compoundOrder.split("-")[1];
            return new UserOrderKey(userOrderId, userProfileId);
        } catch (NumberFormatException e) {
            throw new ResourceNotFoundException("Order id is not a number");
        }
    }

}
