package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import ru.yandex.practicum.javafilmorate.validation.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@RequiredArgsConstructor
public class Film {
    private Integer id;
    @NotBlank(message = "Name фильма не может быть пустым")
    private String name;
    @Size(max = 200, message = "Description фильма не должен содержать больше 200 символов")
    private String description;
    @FilmReleaseDate(message = "ReleaseDate фильма не должен быть раньше 28 декабря 1895 года")
    private LocalDate releaseDate;
    @Positive(message = "Duration фильма не может быть отрицательным")
    private Integer duration;
    @NotNull(message = "Film без Rating быть не может")
    private Mpa mpa;
    private LinkedHashSet<Genre> genres;

    public Film(String name, String description, LocalDate releaseDate, int duration,
                Mpa mpa, LinkedHashSet<Genre> genres) {
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }

    public Film(Integer id, String name, String description, LocalDate releaseDate, Integer duration,
                Mpa mpa, LinkedHashSet<Genre> genres) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.releaseDate = releaseDate;
        this.duration = duration;
        this.mpa = mpa;
        this.genres = genres;
    }

    public void addGenre(Genre genre) {
        if (genres == null) {
            genres = new LinkedHashSet<>();
        }
        genres.add(genre);

    }
}