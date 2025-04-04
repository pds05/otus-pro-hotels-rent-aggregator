package ru.otus.java.pro.result.project.hotelsaggregator.validators;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;

public class UsernameValidation implements ConstraintValidator<UsernameValid, Object> {
    private String email;
    private String phoneNumber;

    @Override
    public void initialize(UsernameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.email = constraintAnnotation.email();
        this.phoneNumber = constraintAnnotation.phoneNumber();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            final Field emailField = value.getClass().getDeclaredField(email);
            emailField.setAccessible(true);

            final Field phoneField = value.getClass().getDeclaredField(phoneNumber);
            phoneField.setAccessible(true);

            Object emailObject = emailField.get(value);
            Object phoneObject = phoneField.get(value);
            return Boolean.logicalOr(emailObject != null, phoneObject != null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            return false;
        }


    }
}
