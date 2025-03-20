package ru.otus.java.pro.result.project.hotels.exceptions;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CustomValidationException extends RuntimeException {
    private String code;
    private List<ValidationFieldError> errors = new ArrayList<>();

    public CustomValidationException(String code, String message, List<ValidationFieldError> errors) {
        super(message);
        this.code = code;
        this.errors = errors;
    }

    public CustomValidationException(String code, String message) {
        super(message);
        this.code = code;
    }

    public void addError(ValidationFieldError error) {
        errors.add(error);
    }

    public String print() {
        return "ValidationException{" +
                "code='" + code + '\'' +
                "message='" + getMessage() + '\'' +
                ", errors=" + errors +
                '}';
    }
}
