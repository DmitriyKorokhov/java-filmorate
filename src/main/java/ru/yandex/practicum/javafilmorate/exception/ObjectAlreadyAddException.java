package ru.yandex.practicum.javafilmorate.exception;

public class ObjectAlreadyAddException extends RuntimeException {
    public ObjectAlreadyAddException(String message) {
        super(message);
    }
}
