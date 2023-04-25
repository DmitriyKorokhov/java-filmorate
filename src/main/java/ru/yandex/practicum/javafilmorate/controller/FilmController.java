package ru.yandex.practicum.javafilmorate.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.Film;

import javax.validation.Valid;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {

    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @GetMapping
    public List<Film> getFilm() {
        log.info("Вывод всех Films");
        return new ArrayList<>(films.values());
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        film.setId(filmId);
        films.put(filmId, film);
        filmId++;
        log.info("Film добавлен", film);
        return film;
    }

    @PutMapping
    public Film updated(@Valid @RequestBody Film film) {
        int id = film.getId();
        if (films.containsKey(id)) {
            films.put(id, film);
            log.info("Film обновлен", film);
        } else {
            log.warn("Film с данным id не существует");
            throw new ValidationException("Film с данным id не существует");
        }
        return film;
    }
}