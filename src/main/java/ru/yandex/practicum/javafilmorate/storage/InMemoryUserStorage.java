package ru.yandex.practicum.javafilmorate.storage;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.exception.*;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Component
public class InMemoryUserStorage implements UserStorage {
    private final Map<Integer, User> users = new HashMap<>();
    private int userId = 1;

    @Override
    public List<User> getAllUsers() {
        log.info("Вывод всех Users");
        return new ArrayList<>(users.values());
    }

    @Override
    public User addUser(User user) {
        int id = user.getId();
        if (users.containsKey(id) || users.containsKey(user.getEmail())) {
            throw new ObjectAlreadyAddException("Пользователь уже существует");
        } else {
            validateUserName(user);
            errorValidate(user);
            user.setId(userId);
            users.put(userId, user);
            userId++;
            log.info("User добавлен", user);
            return user;
        }
    }

    @Override
    public User updateUser(User user) {
        int id = user.getId();
        if (users.containsKey(id)) {
            validateUserName(user);
            errorValidate(user);
            users.put(id, user);
            log.info("User обновлен", user);
            return user;
        } else {
            throw new NotFoundException("User с данным id не существует");
        }
    }

    private void validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            user.setName(user.getLogin());
        }
    }

    private void errorValidate(User user) {
        if (user.getLogin() == null || user.getEmail() == null ||
                (user.getBirthday() != null && user.getBirthday().isAfter(LocalDate.now()))) {
            throw new ValidationException("Некорректные данные");
        }
    }

    @Override
    public User getUserById(int id) {
        if (users.containsKey(id)) {
            log.info("Вывод User с id = ", id);
            return users.get(id);
        } else {
            throw new NotFoundException("User с id = " + id + " не найден");
        }
    }
}
