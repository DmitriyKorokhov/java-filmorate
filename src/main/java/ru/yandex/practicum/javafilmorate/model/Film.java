package ru.yandex.practicum.javafilmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.javafilmorate.validation.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.LinkedHashSet;

@Data
@Builder
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

    public void addGenre(Genre genre) {
        if (genres == null) {
            genres = new LinkedHashSet<>();
        }
        genres.add(genre);
    }
}