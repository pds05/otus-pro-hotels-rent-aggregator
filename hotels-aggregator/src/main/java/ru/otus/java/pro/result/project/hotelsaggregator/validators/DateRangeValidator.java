package ru.otus.java.pro.result.project.hotelsaggregator.validators;

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

            Object beforeObject = beforeDateField.get(value);
            Object afterObject = afterDateField.get(value);
            if (beforeObject == null || afterObject == null) {
                return false;
            }
            final LocalDate beforeDate = (LocalDate) beforeObject;
            final LocalDate afterDate = (LocalDate) afterObject;
            return !beforeDate.isEqual(afterDate) && beforeDate.isBefore(afterDate);
        } catch (NoSuchFieldException | IllegalAccessException ignored) {
            return false;
        }
    }
}
