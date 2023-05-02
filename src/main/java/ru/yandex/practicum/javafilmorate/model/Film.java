package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;
import ru.yandex.practicum.javafilmorate.validation.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private int id;
    @NotBlank(message = "Name фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Description фильма не должен содержать больше 200 символов")
    private String description;
    @FilmReleaseDate(message = "ReleaseDate фильма не должен быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "Duration фильма не может быть отрицательным")
    private Integer duration;
    private Set<Integer> likes;

    public Film(int id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.likes = likes == null ? new HashSet<>() : likes;
    }
}