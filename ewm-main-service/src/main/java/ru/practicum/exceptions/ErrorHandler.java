package ru.practicum.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestControllerAdvice
@Slf4j
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public MainServiceError validationException(final ValidationException e) {
        log.info("SQLIntegrityConstraintViolationException {}", e.getMessage());
        return MainServiceError.builder()
                .message("SQLIntegrityConstraintViolationException" + e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MainServiceError notFoundException(final NotFoundException e) {
        log.info("NotFoundException {}", e.getMessage());
        return MainServiceError.builder()
                .message("NotFoundException " + e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.CONFLICT)
    public MainServiceError anybodyUseEmailOrNameException(final AnybodyUseEmailOrNameException e) {
        log.info("AnybodyUseEmailOrNameException {}", e.getMessage());
        return MainServiceError.builder()
                .message("AnybodyUseEmailOrNameException" + e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public MainServiceError emptyResultDataAccessException(final EmptyResultDataAccessException e) {
        log.info("EmptyResultDataAccessException {}", e.getMessage());
        return MainServiceError.builder()
                .message("AnybodyUseEmailOrNameException" + e.getMessage())
                .status("FORBIDDEN")
                .reason("For the requested operation the conditions are not met.")
                .timestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME))
                .build();
    }
}