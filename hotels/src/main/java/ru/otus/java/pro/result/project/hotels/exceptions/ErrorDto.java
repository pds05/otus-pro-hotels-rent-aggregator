package ru.otus.java.pro.result.project.hotels.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ErrorDto {
    private String code;
    private String message;
    @JsonFormat(pattern = "MM-dd-yyyy HH:mm:ss")
    private LocalDateTime dateTime;

    public ErrorDto(String code, String message) {
        this.code = code;
        this.message = message;
        this.dateTime = LocalDateTime.now();
    }
}
