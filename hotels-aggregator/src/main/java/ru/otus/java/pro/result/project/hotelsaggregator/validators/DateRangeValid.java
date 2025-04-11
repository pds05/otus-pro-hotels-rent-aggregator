package ru.otus.java.pro.result.project.hotelsaggregator.validators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DateRangeValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DateRangeValid {
    String message() default "Check-in date must be before departure date";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String before();

    String after();

}
