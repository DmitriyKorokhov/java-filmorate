package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.storage.MpaDao;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MpaService {

    private final MpaDao mpaStorage;

    public Mpa getMpaById(Integer id) {
        checkMpaExist(id);
        log.info("Вывод Rating с id = %d", id);
        return mpaStorage.getMpaById(id);
    }

    public List<Mpa> getAllMpa() {
        log.info("Вывод всех Ratings");
        return mpaStorage.getAllMpa();
    }

    public void checkMpaExist(int id) {
        if (!mpaStorage.isMpaExistedById(id)) {
            throw new NotFoundException(String.format("Rating с id = %d не сужествует", id));
        }
    }
}