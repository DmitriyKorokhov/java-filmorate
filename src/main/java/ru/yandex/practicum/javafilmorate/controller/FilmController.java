package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.service.FilmService;

import javax.validation.Valid;
import java.util.*;

@RequestMapping("/films")
@Slf4j
@RestController
@RequiredArgsConstructor
public class FilmController {

    private final FilmService filmService;

    @GetMapping
    public List<Film> getAllFilms() {
        return filmService.getAllFilms();
    }

    @PostMapping
    public Film addFilm(@Valid @RequestBody Film film) {
        return filmService.addFilm(film);
    }

    @PutMapping
    public Film updateFilm(@Valid @RequestBody Film film) {
        return filmService.updateFilm(film);
    }

    @GetMapping("/{id}")
    public Film getFilmById(@PathVariable int id) {
        return filmService.getFilmById(id);
    }

    @PutMapping("/{id}/like/{userId}")
    public void likesFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.likesFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeOfTheFilm(@PathVariable int id, @PathVariable int userId) {
        filmService.deleteLikeOfTheFilm(id, userId);
    }

    @GetMapping("/popular")
    public List<Film> listOfFilmsByNumberOfLikes(@RequestParam(value = "count", defaultValue = "10") int count) {
        return filmService.listOfFilmsByNumberOfLikes(count);
    }
}