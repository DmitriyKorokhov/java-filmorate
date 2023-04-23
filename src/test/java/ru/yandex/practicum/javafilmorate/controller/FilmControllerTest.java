package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.Film;


import javax.validation.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getFilm();
        assertEquals(1, savedFilms.size(), "Неверное количество films");
        assertEquals(film, savedFilms.get(0), "Film добавлен некорректно");
    }

    @Test
    void notAddFilmWithEmptyName() {
        film = new Film(1, "", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("name: Name фильма не может быть пустым");
            }
        });
        Assertions.assertEquals("name: Name фильма не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddFilmWithEmptyDescription() {
        film = new Film(1, "The Batman", "",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("description: Description фильма не может быть пустым");
            }
        });
        Assertions.assertEquals("description: Description фильма не может быть пустым", exception.getMessage());
    }


    @Test
    void notAddFilmWithDescriptionLongerThan200symbols() {
        film = new Film(1, "The Batman", "When a series of violent attacks on " +
                "high-ranking officials takes place in the city, " +
                "evidence leads Bruce Wayne into the darkest corners of the underworld, " +
                "where he meets Catwoman, Penguin, Carmine Falcone and the Riddler. " +
                "Now Batman himself is under the gun, who will have to distinguish friend from enemy and " +
                "restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("description: Description фильма не должен содержать больше 200 символов");
            }
        });
        Assertions.assertEquals("description: Description фильма не должен содержать больше 200 символов",
                exception.getMessage());
    }

    @Test
    void notAddFilmWithNullReleaseDate() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.", null, 2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("releaseDate: ReleaseDate фильма не может быть пустым");
            }
        });
        Assertions.assertEquals("releaseDate: ReleaseDate фильма не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddFilmWithReleaseDateIn20December1895() {
        film = new Film(1, "Sea", "The film shows the beach shore with a pier. " +
                "Vacationers jump from the pier into shallow water and come out of the water to the shore.",
                LocalDate.of(1895, Month.DECEMBER, 20), 1);
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getFilm();
        assertEquals(1, savedFilms.size(), "Неверное количество films");
        assertEquals(film, savedFilms.get(0), "Film добавлен некорректно");
    }

    @Test
    void notAddFilmWithReleaseDateEarlierThan20December1895() {
        film = new Film(1, "Blacksmiths", "The film shows the work of a pair of blacksmiths — " +
                "one strikes the workpiece with a hammer, and the other rotates the handle of a hand pump.",
                LocalDate.of(1890, 12, 28), 1);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("releaseDate: ReleaseDate фильма не должен быть раньше 28 декабря 1895 года");
            }
        });
        Assertions.assertEquals("releaseDate: ReleaseDate фильма не должен быть раньше 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    void notAddFilmWithNullDuration() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), null);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("duration: Duration фильма не может быть пустым");
            }
        });
        Assertions.assertEquals("duration: Duration фильма не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddFilmNegativeDuration() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), -2);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("duration: Duration фильма не может быть отрицательным");
            }
        });
        Assertions.assertEquals("duration: Duration фильма не может быть отрицательным", exception.getMessage());
    }

    @Test
    void updateFilm() {
        film = new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2);
        filmController.addFilm(film);
        Film newFilm = new Film(1, "Spider-Man", "Peter becomes a real superhero named Spider-Man, " +
                "who helps people and fights crime. But where there is a superhero, " +
                "sooner or later a supervillain always appears.",
                LocalDate.of(2002, Month.APRIL, 30), 2);
        filmController.updated(newFilm);
        List<Film> savedFilms = filmController.getFilm();
        assertEquals(1, savedFilms.size(), "Неверное количество films");
        assertEquals(newFilm, savedFilms.get(0), "Film добавлен некорректно");
    }
}