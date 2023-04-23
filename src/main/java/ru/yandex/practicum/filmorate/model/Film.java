package ru.yandex.practicum.filmorate.model;

import lombok.Data;
import ru.yandex.practicum.filmorate.validation.FilmReleaseDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
public class Film {
    private int id;
    @NotBlank(message = "Name не может быть пустым")
    private String name;
    @NotBlank(message = "Description не может быть пустым")
    @Size(max = 200, message = "Description не может превышать 200 символов")
    private String description;
    @NotNull(message = "ReleaseDate не может быть пустым")
    @FilmReleaseDate(message = "Дата релиза фильма - раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @NotNull(message = "Duration не может быть пустым")
    @Positive(message = "Duration должна быть положительной")
    private Integer duration;

    public Film(int id, String name, String description, LocalDate releaseDate, Integer duration) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
    }
}