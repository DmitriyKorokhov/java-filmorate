package ru.yandex.practicum.filmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Constraint(validatedBy = {CheckFilmReleaseDate.class})
@Target({ElementType.FIELD, TYPE})
@Retention(RUNTIME)
public @interface FilmReleaseDate {
    String message() default "date invalid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}