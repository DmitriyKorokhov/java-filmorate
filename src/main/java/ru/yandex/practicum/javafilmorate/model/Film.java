package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;
import ru.yandex.practicum.javafilmorate.validation.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;


@Data
public class Film {

    private int id;
    @NotBlank(message = "Name фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Description фильма не должен содержать больше 200 символов")
    @NotBlank(message = "Description фильма не может быть пустым")
    private String description;
    @NotNull(message = "ReleaseDate фильма не может быть пустым")
    @FilmReleaseDate(message = "ReleaseDate фильма не должен быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @NotNull(message = "Duration фильма не может быть пустым")
    @Positive(message = "Duration фильма не может быть отрицательным")
    private Integer duration;

    public Film(int id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}