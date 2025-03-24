package ru.otus.java.pro.result.project.hotels.exceptions;

import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.method.MethodValidationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestValueException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorDto> catchInvalidArgumentRequest(MethodArgumentNotValidException exception) {
        ResponseEntity<ValidationErrorDto> response;
        if (exception.getBindingResult().hasFieldErrors()) {
            response = new ResponseEntity<>(
                    new ValidationErrorDto("VALIDATION_ERROR", "Request not valid",
                            exception.getBindingResult().getFieldErrors().stream().map(ve -> new ValidationFieldErrorDto(ve.getField(), ve.getDefaultMessage())).toList()
                    ),
                    HttpStatus.UNPROCESSABLE_ENTITY);

        } else {
            response = new ResponseEntity<>(
                    new ValidationErrorDto("VALIDATION_ERROR", "Request not valid",
                            exception.getBindingResult().getAllErrors().stream().map(oe -> new ValidationFieldErrorDto("", oe.getDefaultMessage())).toList()
                    ),
                    HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return response;
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ValidationErrorDto> catchValidationException(ConstraintViolationException exception) {
        log.warn("Request not valid: {}", exception.getMessage());
        return new ResponseEntity<>(
                new ValidationErrorDto("VALIDATION_ERROR", "Request not valid",
                        exception.getConstraintViolations().stream().map(cv -> new ValidationFieldErrorDto(cv.getPropertyPath().toString().split("\\.")[1], cv.getMessage())).collect(Collectors.toUnmodifiableList())
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(value = MethodValidationException.class)
    public ResponseEntity<ValidationErrorDto> catchValidationException(MethodValidationException exception) {
        log.warn("Request not valid: {}", exception.getMessage());
        return new ResponseEntity<>(
                new ValidationErrorDto("VALIDATION_ERROR", "Request not valid",
                        exception.getParameterValidationResults().stream().map(result -> new ValidationFieldErrorDto(
                                        result.getMethodParameter().getParameterName(),
                                        result.getResolvableErrors().stream().findFirst().orElseThrow(() -> new ApplicationException("Validation parse error")).getDefaultMessage()))
                                .collect(Collectors.toUnmodifiableList())
                ),
                HttpStatus.UNPROCESSABLE_ENTITY
        );
    }

    @ExceptionHandler(value = MissingServletRequestParameterException.class)
    public ResponseEntity<ValidationErrorDto> catchValidationException(MissingServletRequestParameterException exception) {
        log.warn("Request missing parameter {}, message={}", exception.getParameterName(), exception.getMessage());
        return new ResponseEntity<>(
                new ValidationErrorDto("REQUEST_PARAMETER_ERROR", "Missing parameter",
                        List.of(new ValidationFieldErrorDto(exception.getParameterName(), exception.getMessage()))
                ),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ErrorDto> catchException(Exception exception) {
        log.error("Unexpected error has occurred: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(new ErrorDto("INTERNAL_SERVER_ERROR", exception.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(ResourceNotFoundException e) {
        log.warn("Resource not found: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorDto("RESOURCE_NOT_FOUND", e.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(value = BusinessLogicException.class)
    public ResponseEntity<ErrorDto> catchResourceNotFoundException(BusinessLogicException e) {
        log.error("Business logic error: {}", e.getMessage());
        return new ResponseEntity<>(new ErrorDto(e.getCode(), e.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
