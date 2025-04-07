package ru.otus.java.pro.result.project.hotelsaggregator.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProviderException extends RuntimeException {
  private final String code;

    public ProviderException(String code, String message) {
        super(message);
        this.code = code;
    }
}
