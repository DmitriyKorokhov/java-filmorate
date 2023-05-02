package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.exception.ObjectAlreadyAddException;
import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Integer, Film> films = new HashMap<>();
    private int filmId = 1;

    @Override
    public List<Film> getAllFilms() {
        log.info("Вывод всех Films");
        return new ArrayList<>(films.values());
    }

    @Override
    public Film addFilm(Film film) {
        int id = film.getId();
        if (!films.containsKey(id)) {
            film.setId(filmId);
            films.put(filmId, film);
            filmId++;
            log.info("Film добавлен", film);
            return film;
        } else {
            throw new ObjectAlreadyAddException("Film с id = " + id + "уже добавлен");
        }
    }

    @Override
    public Film updateFilm(Film film) {
        int id = film.getId();
        if (films.containsKey(id)) {
            films.replace(id, film);
            log.info("Film обновлен", film);
            return film;
        } else {
            throw new NotFoundException("Film с данным id не существует");
        }
    }

    @Override
    public Film getFilmById(Integer id) {
        if (films.containsKey(id)) {
            log.info("Вывод Film с id = ", id);
            return films.get(id);
        } else {
            throw new NotFoundException("Film с id = " + id + " не найден");
        }
    }
}
