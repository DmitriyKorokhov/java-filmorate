package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmStorage;

import java.util.Comparator;
import java.util.List;

@Slf4j
@Service
public class FilmService {
    private final FilmStorage filmStorage;

    @Autowired
    public FilmService(FilmStorage filmStorage) {
        this.filmStorage = filmStorage;
    }

    public List<Film> getAllFilms() {
        return filmStorage.getAllFilms();
    }

    public Film addFilm(Film film) {
        return filmStorage.addFilm(film);
    }

    public Film updateFilm(Film film) {
        return filmStorage.updateFilm(film);
    }

    public Film getFilmById(int id) {
        return filmStorage.getFilmById(id);
    }

    public Film likesFilm(int filmId, int userId) {
        Film film = getFilmById(filmId);
        if (film.getLikes().contains(userId)) {
            throw new ObjectAlreadyAddException("Лайк User с id = " + userId +
                    " к фильму с id = " + filmId + " уже добавлен");
        } else {
            film.getLikes().add(userId);
            log.info("Лайк User с id = ", userId, " к фильму с id = ", filmId, " добавлен");
            updateFilm(film);
            return film;
        }
    }

    public Film deleteLikeOfTheFilm(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Идентификаторы User или Film не могут быть отрицательными");
        } else {
            Film film = getFilmById(filmId);
            film.getLikes().remove(userId);
            log.info("Лайк User с id = ", userId, " к фильму с id = ", filmId, " удален");
            updateFilm(film);
            return film;
        }
    }

    public List<Film> listOfFilmsByNumberOfLikes(Integer count) {
        if (count == null || count < 1) {
            count = 10;
        }
        List<Film> filmSort = filmStorage.getAllFilms();
        if (filmSort.size() < count) {
            count = filmSort.size();
        }
        filmSort.sort(Comparator.comparingInt(f -> -1 * f.getLikes().size()));
        return filmSort.subList(0, count);
    }
}
