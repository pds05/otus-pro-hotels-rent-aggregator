package ru.otus.java.pro.result.project.hotels.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ValidationErrorDto {
    private String code;
    private String message;
    private List<ValidationFieldErrorDto> errors;
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    public ValidationErrorDto(String code, String message, List<ValidationFieldErrorDto> errors) {
        this.code = code;
        this.message = message;
        this.errors = errors;
        this.dateTime = LocalDateTime.now();
    }
}
