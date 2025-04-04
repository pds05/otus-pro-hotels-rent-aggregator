package ru.otus.java.pro.result.project.hotelsaggregator.exceptions;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
