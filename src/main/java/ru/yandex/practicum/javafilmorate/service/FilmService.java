package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.storage.GenreDao;
import ru.yandex.practicum.javafilmorate.storage.MpaDao;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.FilmDao;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {

    private final FilmDao filmDao;
    private final MpaDao mpaDao;
    private final GenreDao genreDao;

    public List<Film> getAllFilms() {
        List<Film> films = filmDao.getAllFilms();
        films = genreDao.loadGenres(films);
        log.info("Вывод списка всех Films");
        return films;
    }

    public Film addFilm(Film film) {
        mpaDao.isMpaExistedById(film.getMpa().getId());
        filmDao.addFilm(film);
        genreDao.addFilmGenre(film);
        log.info("Добавление нового фильма с id = " + film.getId());
        return film;
    }

    public void deleteAllFilms() {
        log.info("Все Films удалены");
        filmDao.deleteAllFilms();
    }

    public Film deleteFilmById(int id) {
        checkFilmExist(id);
        log.info("Film с id = " + id + " удален");
        return filmDao.deleteFilmById(id);
    }

    public Film updateFilm(Film film) {
        checkFilmExist(film.getId());
        genreDao.updateFilmGenre(film);
        mpaDao.isMpaExistedById(film.getMpa().getId());
        filmDao.updateFilm(film);
        log.info("Film c id = " + film.getId() + " обнавлен");
        return film;
    }

    public Film getFilmById(int id) {
        checkFilmExist(id);
        Film film = filmDao.getFilmById(id);
        log.info("Вывод Film с id = " + id);
        return genreDao.loadGenres(List.of(film)).get(0);
    }

    public List<Film> listOfFilmsByNumberOfLikes(Integer count) {
        return filmDao.listOfFilmsByNumberOfLikes(count);
    }

    public void checkFilmExist(int id) {
        if (!filmDao.isFilmExistedById(id)) {
            throw new NotFoundException(String.format("Не существует Film с id = " + id));
        }
    }
}
