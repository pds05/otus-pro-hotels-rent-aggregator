package ru.otus.java.pro.result.project.messageprocessor.exceptions;

public class ProviderException extends RuntimeException {
    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }
    public ProviderException(String message) {
        super(message);
    }

}
