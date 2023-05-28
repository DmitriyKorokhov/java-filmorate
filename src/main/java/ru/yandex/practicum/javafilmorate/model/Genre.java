package ru.yandex.practicum.javafilmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@Builder
public class Genre {
    @NotNull
    private Integer id;
    private String name;
}