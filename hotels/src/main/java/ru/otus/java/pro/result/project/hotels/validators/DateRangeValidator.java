package ru.otus.java.pro.result.project.hotels.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateRangeValidator implements ConstraintValidator<DateRangeValid, Object> {
    private String beforeFieldName;
    private String afterFieldName;

    @Override
    public void initialize(DateRangeValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        beforeFieldName = constraintAnnotation.before();
        afterFieldName = constraintAnnotation.after();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Field beforeDateField = value.getClass().getDeclaredField(beforeFieldName);
            beforeDateField.setAccessible(true);

            final Field afterDateField = value.getClass().getDeclaredField(afterFieldName);
            afterDateField.setAccessible(true);

            final LocalDate beforeDate = (LocalDate) beforeDateField.get(value);
            final LocalDate afterDate = (LocalDate) afterDateField.get(value);
            return beforeDate.isEqual(afterDate) || beforeDate.isBefore(afterDate);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            return false;
        }
    }
}
