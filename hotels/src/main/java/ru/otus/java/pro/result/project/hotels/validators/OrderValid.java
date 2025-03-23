package ru.otus.java.pro.result.project.hotels.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = OrderValidator.class)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface OrderValid {
    String message() default "Order is invalid";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default {};

}
