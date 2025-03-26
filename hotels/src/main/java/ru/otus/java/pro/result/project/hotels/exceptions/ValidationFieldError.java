package ru.otus.java.pro.result.project.hotels.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ValidationFieldError {
    private String field;
    private String message;
}
