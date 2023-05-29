package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers();

    User addUser(User user);

    void deleteAllUsers();

    User deleteUserById(int id);

    User updateUser(User user);

    User getUserById(int id);

    boolean isUserExistedById(int id);
}
