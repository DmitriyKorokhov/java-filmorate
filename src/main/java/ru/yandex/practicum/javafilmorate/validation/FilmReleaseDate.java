package ru.yandex.practicum.javafilmorate.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Constraint(validatedBy = CheckFilmReleaseDate.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface FilmReleaseDate {
    String message() default "{invalid date}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}