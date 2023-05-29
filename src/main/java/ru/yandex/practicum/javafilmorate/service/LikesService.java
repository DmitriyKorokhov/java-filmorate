package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.storage.LikesDao;

@Slf4j
@Service
@RequiredArgsConstructor
public class LikesService {

    private final LikesDao likesDao;
    private final FilmService filmService;

    public void likesFilm(int filmId, int userId) {
        filmService.checkFilmExist(userId);
        likesDao.addLike(filmId, userId);
        log.info("Лайк User с id = " + userId + " к фильму с id = " + filmId + " добавлен");
    }

    public Film deleteLikeOfTheFilm(int filmId, int userId) {
        if (filmId < 0 || userId < 0) {
            throw new NotFoundException("Идентификаторы User или Film не могут быть отрицательными");
        } else {
            Film film = filmService.getFilmById(filmId);
            likesDao.deleteLike(filmId, userId);
            log.info("Лайк User с id = " + userId + " к фильму с id = " + filmId + " удален");
            return film;
        }
    }
}
