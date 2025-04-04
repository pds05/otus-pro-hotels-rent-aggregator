package ru.otus.java.pro.result.project.hotels.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = RequestParametersValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestParametersValid {
    String message() default "Parameter not valid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

    Class<?> source();
}
