package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserDao;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserDao userDao;

    public User addUser(User user) {
        validateUserName(user);
        log.info("Добавлен новый User");
        return userDao.addUser(user);
    }

    public void deleteAllUsers() {
        log.info("Все Users удалены");
        userDao.deleteAllUsers();
    }

    public User deleteUserById(int id) {
        checkUserExist(id);
        log.info("User с id = " + id + " удален");
        return userDao.deleteUserById(id);
    }

    public User updateUser(User user) {
        validateUserName(user);
        checkUserExist(user.getId());
        log.info("User c id = " + user.getId() + " обнавлен");
        return userDao.updateUser(user);
    }

    public List<User> getAllUsers() {
        log.info("Вывод всех пользователей");
        return new ArrayList<>(userDao.getAllUsers());
    }

    public User getUserById(int id) {
        checkUserExist(id);
        log.info("Вывод User с id = " + id);
        return userDao.getUserById(id);
    }

    public void validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("User с id = " + user.getId() + " зарегестрирован с login в name");
            user.setName(user.getLogin());
        }
    }

    public void checkUserExist(int id) {
        if (!userDao.isUserExistedById(id)) {
            throw new NotFoundException(String.format("Не существует User с id = " + id));
        }
    }
}