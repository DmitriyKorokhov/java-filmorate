package ru.yandex.practicum.filmorate.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class CheckFilmReleaseDate implements ConstraintValidator<FilmReleaseDate, LocalDate> {

    private static final LocalDate minimumReleaseDate = LocalDate.of(1895, 12, 28);

    @Override
    public boolean isValid(LocalDate releaseDate, ConstraintValidatorContext constraintValidatorContext) {
        boolean checkReleaseDate = releaseDate != null && releaseDate.isAfter(minimumReleaseDate);
        return checkReleaseDate;
    }
}