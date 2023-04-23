package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import javax.validation.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FilmControllerTest {
    private static FilmController filmController;
    private Film film;
    private Validator validator;

    @BeforeEach
    void beforeEach() {
        filmController = new FilmController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void addFilm() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getFilm();
        assertEquals(film, savedFilms.get(0), "Film добавлен некорректно");
        assertEquals(1, savedFilms.size(), "Неверное количество films");
    }

    @Test
    void notAddFilmWithEmptyName() {
        film = new Film(1, "", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("name: Name не может быть пустым");
            }
        });
        Assertions.assertEquals("name: Name не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddFilmWithDescriptionLongerThan200characters() {
        film = new Film(1, "The Batman", "When a series of violent attacks on high-ranking officials takes " +
                "place in the city, evidence leads Bruce Wayne into the darkest corners of the underworld, " +
                "where he meets Catwoman, Penguin, Carmine Falcone and the Riddler. " +
                "Now Batman himself is under the gun, who will have to distinguish friend from enemy " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> constraintViolations = validator.validate(film);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("description: Description не может превышать 200 символов");
            }
        });
        Assertions.assertEquals("description: Description не может превышать 200 символов",
                exception.getMessage());
    }

    @Test
    void notAddFilmWithNullReleaseDate() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham",
                null, 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("releaseDate: ReleaseDate не может быть пустым");
            }
        });
        Assertions.assertEquals("releaseDate: ReleaseDate не может быть пустым",
                exception.getMessage());
    }

    @Test
    void addFilmWithReleaseDateIn28December1985() {
        film = new Film(1, "Sea", "The film shows the beach shore with a pier. " +
                "Vacationers jump from the pier into shallow water and come out of the water to the shore.",
                LocalDate.of(1895, Month.DECEMBER, 20), 1);
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getFilm();
        assertEquals(film, savedFilms.get(0), "ReleaseDate не корректна");
    }

    @Test
    void notAddFilmWithReleaseDateEarlierThan28December1985() {
        film = new Film(1, "Carmencita", "The film shows a dancer named Carmencita dressed in " +
                "a wide dress with supporting hoops. She dances her dance on a wooden stage.",
                LocalDate.of(1884, Month.MARCH, 10), 1);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("releaseDate: Дата релиза фильма - раньше 28 декабря 1895 года");
            }
        });
        Assertions.assertEquals("releaseDate: Дата релиза фильма - раньше 28 декабря 1895 года",
                exception.getMessage());
    }

    @Test
    void notAddFilmWithNullDuration() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham",
                LocalDate.of(2022, Month.MARCH, 1), null);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("duration: Duration не может быть пустым");
            }
        });
        Assertions.assertEquals("duration: Duration не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddFilmWithNegativeDuration() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham",
                LocalDate.of(2022, Month.MARCH, 1), -2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("duration: Duration должна быть положительной");
            }
        });
        Assertions.assertEquals("duration: Duration должна быть положительной", exception.getMessage());
    }

    @Test
    void updateFilm() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        filmController.addFilm(film);
        Film newFilm = new Film(1, "Spider-Man", "Peter becomes a real superhero named Spider-Man, " +
                "who helps people and fights crime. But where there is a superhero, " +
                "sooner or later a supervillain always appears",
                LocalDate.of(2002, Month.APRIL, 30), 2);
        filmController.updateFilm(newFilm);
        List<Film> savedFilms = filmController.getFilm();
        assertEquals(newFilm, savedFilms.get(0), "Film обновлен некорректно");
        assertEquals(1, savedFilms.size(), "Неверное количество films");
    }
}