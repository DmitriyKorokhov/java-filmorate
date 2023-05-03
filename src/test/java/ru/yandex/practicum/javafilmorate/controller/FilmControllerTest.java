package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;
import ru.yandex.practicum.javafilmorate.storage.InMemoryFilmStorage;

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
        filmController = new FilmController(new FilmService(new InMemoryFilmStorage()));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void addFilm() {
        film = Film.builder()
                .id(1)
                .name("The Batman")
                .description("Batman will have to distinguish friend from foe " +
                        "and restore justice in the name of Gotham.")
                .releaseDate(LocalDate.of(2022, Month.MARCH, 1))
                .duration(2)
                .build();
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getAllFilms();
        assertEquals(1, savedFilms.size(), "Неверное количество films");
        assertEquals(film, savedFilms.get(0), "Film добавлен некорректно");
    }

    @Test
    void notAddFilmWithEmptyName() {
        film = Film.builder()
                .id(1)
                .name("")
                .description("Batman will have to distinguish friend from foe " +
                        "and restore justice in the name of Gotham.")
                .releaseDate(LocalDate.of(2022, Month.MARCH, 1))
                .duration(2)
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("name: Name фильма не может быть пустым");
            }
        });
        Assertions.assertEquals("name: Name фильма не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddFilmWithDescriptionLongerThan200symbols() {
        film = Film.builder()
                .id(1)
                .name("The Batman")
                .description("When a series of violent attacks on " +
                        "high-ranking officials takes place in the city, " +
                        "evidence leads Bruce Wayne into the darkest corners of the underworld, " +
                        "where he meets Catwoman, Penguin, Carmine Falcone and the Riddler. " +
                        "Now Batman himself is under the gun, who will have to distinguish friend from enemy and " +
                        "restore justice in the name of Gotham.")
                .releaseDate(LocalDate.of(2022, Month.MARCH, 1))
                .duration(2)
                .build();
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
    void notAddFilmWithReleaseDateIn20December1895() {
        film = Film.builder()
                .id(1)
                .name("Sea")
                .description("The film shows the beach shore with a pier. " +
                        "Vacationers jump from the pier into shallow water and come out of the water to the shore.")
                .releaseDate(LocalDate.of(1895, Month.DECEMBER, 20))
                .duration(1)
                .build();
        filmController.addFilm(film);
        List<Film> savedFilms = filmController.getAllFilms();
        assertEquals(1, savedFilms.size(), "Неверное количество films");
        assertEquals(film, savedFilms.get(0), "Film добавлен некорректно");
    }

    @Test
    void notAddFilmWithReleaseDateEarlierThan20December1895() {
        film = Film.builder()
                .id(1)
                .name("Blacksmiths")
                .description("The film shows the work of a pair of blacksmiths — " +
                        "one strikes the workpiece with a hammer, and the other rotates the handle of a hand pump.")
                .releaseDate(LocalDate.of(1890, 12, 28))
                .duration(1)
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<Film>> violations = validator.validate(film);
            if (!violations.isEmpty()) {
                throw new ValidationException("releaseDate: ReleaseDate фильма не должен быть раньше 28 декабря 1895 года");
            }
        });
        Assertions.assertEquals("releaseDate: ReleaseDate фильма не должен быть раньше 28 декабря 1895 года", exception.getMessage());
    }

    @Test
    void notAddFilmNegativeDuration() {
        film = Film.builder()
                .id(1)
                .name("The Batman")
                .description("Batman will have to distinguish friend from foe " +
                        "and restore justice in the name of Gotham.")
                .releaseDate(LocalDate.of(2022, Month.MARCH, 1))
                .duration(-2)
                .build();
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
        film = Film.builder()
                .id(1)
                .name("The Batman")
                .description("Batman will have to distinguish friend from foe " +
                        "and restore justice in the name of Gotham.")
                .releaseDate(LocalDate.of(2022, Month.MARCH, 1))
                .duration(2)
                .build();
        filmController.addFilm(film);
        Film newFilm = Film.builder()
                .id(1)
                .name("Spider-Man")
                .description("Peter becomes a real superhero named Spider-Man, " +
                        "who helps people and fights crime. But where there is a superhero, " +
                        "sooner or later a supervillain always appears.")
                .releaseDate(LocalDate.of(2002, Month.APRIL, 30))
                .duration(2)
                .build();
        filmController.updateFilm(newFilm);
        List<Film> savedFilms = filmController.getAllFilms();
        assertEquals(1, savedFilms.size(), "Неверное количество films");
        assertEquals(newFilm, savedFilms.get(0), "Film добавлен некорректно");
    }
}