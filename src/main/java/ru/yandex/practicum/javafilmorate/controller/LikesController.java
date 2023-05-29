package ru.yandex.practicum.javafilmorate.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.javafilmorate.service.LikesService;

@RequestMapping("/films")
@Slf4j
@RestController
@RequiredArgsConstructor
public class LikesController {

    private final LikesService likesFilm;

    @PutMapping("/{id}/like/{userId}")
    public void likesFilm(@PathVariable int id, @PathVariable int userId) {
        likesFilm.likesFilm(id, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLikeOfTheFilm(@PathVariable int id, @PathVariable int userId) {
        likesFilm.deleteLikeOfTheFilm(id, userId);
    }
}
