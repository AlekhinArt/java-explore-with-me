package ru.practicum.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<String> throwable(final Throwable e) {
        log.info("Throwable : " + e.getMessage());
        return new ResponseEntity<>("Throwable " +
                e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GatewayError unsupportedStatusException(final MethodArgumentTypeMismatchException e) {
        log.debug("MethodArgumentTypeMismatchException {}", e.getMessage());
        return GatewayError.builder()
                .message(e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GatewayError parameterIsNotPresent(final MissingServletRequestParameterException e) {
        log.debug("MissingServletRequestParameterException {}", e.getMessage());
        return GatewayError.builder()
                .message(e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GatewayError notValidException(final ConstraintViolationException e) {
        log.debug("ValidationException {}", e.getMessage());
        return GatewayError.builder()
                .message(e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();

    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public GatewayError notValidException(final MethodArgumentNotValidException e) {
        log.debug("ValidationException {}", e.getMessage());
        return GatewayError.builder()
                .message(e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

}
