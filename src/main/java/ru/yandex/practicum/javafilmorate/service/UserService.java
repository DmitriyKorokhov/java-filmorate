package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.storage.FriendshipDao;
import ru.yandex.practicum.javafilmorate.exception.NotFoundException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserDao;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    public final FriendshipDao friendshipDao;

    public User addUser(User user) {
        validateUserName(user);
        log.info("Добавлен новый User");
        return userDao.addUser(user);
    }

    public User updateUser(User user) {
        validateUserName(user);
        checkUserExist(user.getId());
        log.info("User c id = %d обнавлен", user.getId());
        return userDao.updateUser(user);
    }

    public List<User> getAllUsers() {
        log.info("Вывод всех пользователей");
        return new ArrayList<>(userDao.getAllUsers());
    }

    public User getUserById(int id) {
        checkUserExist(id);
        log.info("Вывод User с id = %d", id);
        return userDao.getUserById(id);
    }

    public void addFriend(int id, int friendId) {
        checkUserExist(id);
        checkUserExist(friendId);
        log.info("User с id = ", friendId, "добавлен в список друзей User c id = %d", id);
        friendshipDao.addFriend(id, friendId);
    }

    public void deleteFriendById(int id, int friendId) {
        log.info("User с id = ", friendId, "удален из списка друзей у User c id = ", id);
        friendshipDao.deleteFriend(id, friendId);
    }

    public List<User> getOfAllUsersFriends(int id) {
        List<User> friendsList = new ArrayList<>(friendshipDao.getAllFriends(id));
        log.info("Вывод всех друзей User с id = %d", id);
        return friendsList;
    }

    public List<User> getCommonFriends(int id, int otherId) {
        List<User> listUsersFriend = getOfAllUsersFriends(id);
        List<User> listOtherUsersFriend = getOfAllUsersFriends(otherId);
        listUsersFriend.retainAll(listOtherUsersFriend);
        log.info("Список друзей User с id = ",id, "общих с User, id которого", otherId);
        return listUsersFriend;
    }

    public void validateUserName(User user) {
        if (user.getName() == null || user.getName().isBlank()) {
            log.info("User с id = %d зарегестрирован с login в name", user.getId());
            user.setName(user.getLogin());
        }
    }

    public void checkUserExist(int id) {
        if (!userDao.isUserExistedById(id)) {
            throw new NotFoundException(String.format("User с id = %d не сужествует", id));
        }
    }
}