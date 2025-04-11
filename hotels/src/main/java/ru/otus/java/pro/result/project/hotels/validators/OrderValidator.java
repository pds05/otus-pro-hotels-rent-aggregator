package ru.otus.java.pro.result.project.hotels.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderValidator implements ConstraintValidator<OrderValid, String> {
    @Override
    public void initialize(OrderValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null) return false;
        Matcher matcher = Pattern.compile("\\d{8}-\\d+").matcher(s);
        return matcher.matches();
    }
}
