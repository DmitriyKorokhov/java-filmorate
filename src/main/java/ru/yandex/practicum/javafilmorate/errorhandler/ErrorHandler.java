package ru.yandex.practicum.javafilmorate.errorhandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.yandex.practicum.javafilmorate.exception.*;

import javax.validation.ValidationException;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleValidation(final ValidationException e) {
        log.warn("Ошибка - переданные данные некорректны объекта", HttpStatus.resolve(400));
        return Map.of(
                "Ошибка валидации", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleNotFound(final NotFoundException e) {
        log.warn("Ошибка - искомый объект удален или не вводился", HttpStatus.resolve(404));
        return Map.of(
                "Ошибка - объект не найден", e.getMessage()
        );
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Map<String, String> handleAlreadyAdd(final ObjectAlreadyAddException e) {
        log.warn("Ошибка - объект уже существует", HttpStatus.resolve(500));
        return Map.of(
                "Ошибка - объект уже существует", e.getMessage()
        );
    }
}
