package ru.yandex.practicum.javafilmorate.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.storage.GenreDao;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Genre;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class GenreService {

    private final GenreDao genreDao;

    public List<Genre> getAllGenres() {
        log.info("Вывод всех Genre");
        return genreDao.getAllGenres();
    }

    public Genre getGenreById(int id) {
        checkGenreExist(id);
        log.info("Вывод Genre с id = %d", id);
        return genreDao.getGenreById(id);
    }

    public void checkGenreExist(int id) {
        if (!genreDao.isGenreExistedById(id)) {
            throw new NotFoundException(String.format("Genre с id = %d не сужествует", id));
        }
    }
}
