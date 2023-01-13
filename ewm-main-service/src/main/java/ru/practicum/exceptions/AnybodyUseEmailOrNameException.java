package ru.practicum.exceptions;

public class AnybodyUseEmailOrNameException extends RuntimeException {
    public AnybodyUseEmailOrNameException(String message) {
        super(message);
    }
}
