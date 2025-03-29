package ru.otus.java.pro.result.project.hotelsaggregator.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = UsernameValidation.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UsernameValid {
    String message() default "Email or phoneNumber must be set";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String email() default "email";

    String phoneNumber() default "phoneNumber";
}
