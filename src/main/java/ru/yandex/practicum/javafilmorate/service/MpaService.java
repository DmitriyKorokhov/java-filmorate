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

    private final MpaDao mpaDao;

    public Mpa getMpaById(Integer id) {
        checkMpaExist(id);
        log.info("Вывод Rating с id = " + id);
        return mpaDao.getMpaById(id);
    }

    public List<Mpa> getAllMpa() {
        log.info("Вывод всех Ratings");
        return mpaDao.getAllMpa();
    }

    public void checkMpaExist(int id) {
        if (!mpaDao.isMpaExistedById(id)) {
            throw new NotFoundException(String.format("Rating с id = " + id + " не сужествует"));
        }
    }
}