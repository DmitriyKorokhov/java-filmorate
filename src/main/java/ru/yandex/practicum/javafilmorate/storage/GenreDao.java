package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.List;

public interface GenreDao {
    void addFilmGenre(Film film);

    Genre getGenreById(int id);

    List<Genre> getAllGenres();

    void updateFilmGenre(Film film);

    List<Film> loadGenres(List<Film> films);

    boolean isGenreExistedById(int id);
}
