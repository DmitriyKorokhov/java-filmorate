package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Film;

import java.util.List;

public interface FilmDao {
    List<Film> getAllFilms();

    void addFilm(Film film);

    Film updateFilm(Film film);

    Film getFilmById(int id);

    List<Film> listOfFilmsByNumberOfLikes(Integer count);

    boolean isFilmExistedById(int id);
}
