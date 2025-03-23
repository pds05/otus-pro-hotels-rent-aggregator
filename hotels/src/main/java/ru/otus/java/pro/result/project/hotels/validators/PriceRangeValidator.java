package ru.otus.java.pro.result.project.hotels.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class PriceRangeValidator implements ConstraintValidator<PriceRangeValid, Object> {
    private String minFieldName;
    private String maxFieldName;

    @Override
    public void initialize(PriceRangeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        minFieldName = constraintAnnotation.min();
        maxFieldName = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Field minField = value.getClass().getDeclaredField(minFieldName);
            minField.setAccessible(true);
            final Field maxField = value.getClass().getDeclaredField(maxFieldName);
            maxField.setAccessible(true);

            int min = Integer.parseInt(minField.get(value).toString());
            int max = Integer.parseInt(maxField.get(value).toString());
            if (min > 0 && max > 0) {
                return max >= min;
            }
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }
    }
}
