package ru.yandex.practicum.javafilmorate.model;

import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.javafilmorate.validation.FilmReleaseDate;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
@Builder
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

    public Set<Integer> getLikes() {
        if (likes == null) {
            likes = new HashSet<>();
        }
        return likes;
    }
}