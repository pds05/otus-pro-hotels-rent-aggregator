package ru.otus.java.pro.result.project.hotels.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import ru.otus.java.pro.result.project.hotels.entities.*;

import java.util.List;

@RequiredArgsConstructor
public class RequestParametersValidator implements ConstraintValidator<RequestParametersValid, List<String>> {
    private final RequestParametersCache cache;
    Class<?> clazz;

    @Override
    public void initialize(RequestParametersValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        clazz = constraintAnnotation.source();
    }

    @Override
    public boolean isValid(List<String> request, ConstraintValidatorContext constraintValidatorContext) {
        if (request == null || request.isEmpty()) {
            return true;
        } else if (clazz == CtHotelType.class) {
            return request.stream().allMatch(param -> cache.getHotelTypes().stream().anyMatch(hotelType -> param.equalsIgnoreCase(hotelType.getTitle())));
        } else if (clazz == HotelAmenity.class) {
            return request.stream().allMatch(param -> cache.getHotelAmenities().stream().anyMatch(amenity -> param.equalsIgnoreCase(amenity.getTitle())));
        } else if (clazz == HotelRoomAmenity.class) {
            return request.stream().allMatch(param -> cache.getHotelRoomAmenities().stream().anyMatch(amenity -> param.equalsIgnoreCase(amenity.getTitle())));
        } else if (clazz == CtHotelFeedType.class) {
            return request.stream().allMatch(param -> cache.getCtHotelFeedTypes().stream().anyMatch(feed -> param.equalsIgnoreCase(feed.getTitle())));
        } else if (clazz == CtHotelBedType.class) {
            return request.stream().allMatch(param -> cache.getCtHotelBedTypes().stream().anyMatch(bed -> param.equalsIgnoreCase(bed.getTitle())));
        }
        return false;
    }
}
