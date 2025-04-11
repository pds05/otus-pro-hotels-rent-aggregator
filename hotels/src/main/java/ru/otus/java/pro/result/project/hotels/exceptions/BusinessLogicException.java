package ru.otus.java.pro.result.project.hotels.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BusinessLogicException extends RuntimeException {
    private String code;

    public BusinessLogicException(String code, String message) {
        super(message);
        this.code = code;
    }
}
