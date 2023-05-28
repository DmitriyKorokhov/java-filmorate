package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.storage.GenreDao;
import ru.yandex.practicum.javafilmorate.storage.LikesDao;
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
    private final MpaDao daoStorage;
    private final GenreDao genreDao;
    private final LikesDao likesDao;

    public List<Film> getAllFilms() {
        List<Film> films = filmDao.getAllFilms();
        films = genreDao.loadGenres(films);
        log.info("Вывод списка всех Films");
        return films;
    }

    public Film addFilm(Film film) {
        daoStorage.isMpaExistedById(film.getMpa().getId());
        filmDao.addFilm(film);
        genreDao.addFilmGenre(film);
        log.info("Добавление нового фильма с id = %d", film.getId());
        return film;
    }

    public Film updateFilm(Film film) {
        checkFilmExist(film.getId());
        genreDao.updateFilmGenre(film);
        daoStorage.isMpaExistedById(film.getMpa().getId());
        filmDao.updateFilm(film);
        log.info("Film c id = %d обнавлен", film.getId());
        return film;
    }

    public Film getFilmById(int id) {
        checkFilmExist(id);
        Film film = filmDao.getFilmById(id);
        log.info("Вывод Film с id = %d", id);
        return genreDao.loadGenres(List.of(film)).get(0);
    }

    public void likesFilm(int filmId, int userId) {

        checkFilmExist(userId);
        likesDao.addLike(filmId, userId);
        log.info("Лайк User с id = ", userId, " к фильму с id = ", filmId, " добавлен");
    }

    public Film deleteLikeOfTheFilm(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Идентификаторы User или Film не могут быть отрицательными");
        } else {
            Film film = getFilmById(filmId);
            likesDao.deleteLike(filmId, userId);
            log.info("Лайк User с id = ", userId, " к фильму с id = ", filmId, " удален");
            return film;
        }
    }

    public List<Film> listOfFilmsByNumberOfLikes(Integer count) {
        return filmDao.listOfFilmsByNumberOfLikes(count);
    }

    public void checkFilmExist(int id) {
        if (!filmDao.isFilmExistedById(id)) {
            throw new NotFoundException(String.format("Film с id = %d не сужествует", id));
        }
    }
}
