package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.util.List;

public interface MpaDao {
    Mpa getMpaById(int id);

    List<Mpa> getAllMpa();

    boolean isMpaExistedById(int id);
}
