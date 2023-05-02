package ru.yandex.practicum.javafilmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.exception.ValidationException;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.UserStorage;

import java.util.*;

@Service
@Slf4j
public class UserService {
    private final UserStorage userStorage;

    @Autowired
    public UserService(UserStorage userStorage) {
        this.userStorage = userStorage;
    }

    public User addUser(User user) {
        return userStorage.addUser(user);
    }

    public User updateUser(User user) {
        return userStorage.updateUser(user);
    }

    public List<User> getAllUsers() {
        return userStorage.getAllUsers();
    }

    public User getUserById(int id) {
        return userStorage.getUserById(id);
    }

    public void addFriend(int id, int friendId) {
        User user = getUserById(id);
        User newFried = getUserById(friendId);
        if (user.getFriends().contains(friendId)) {
            throw new ValidationException("Friend с id = " + friendId
                    + " уже является другом User c id = " + id);
        } else {
            user.getFriends().add(friendId);
            newFried.getFriends().add(id);
            log.info("User с id = ", friendId, "добавлен в список друзей User c id = ", id);
        }
    }

    public void deleteFriendById(int id, int friendId) {
        log.info("User с id = ", friendId, "удален из списка друзей у User c id = ", id);
        getUserById(id).getFriends().remove(friendId);
        getUserById(friendId).getFriends().remove(id);
    }

    public List<User> getOfAllUsersFriends(int id) {
        User user = getUserById(id);
        List<User> friendsList = new ArrayList<>();
        for (Integer friendId : user.getFriends()) {
            User friend = getUserById(friendId);
            if (friend != null) {
                friendsList.add(friend);
            }
        }
        return friendsList;
    }

    public List<User> getCommonFriends(int id, int otherId) {
        User user = getUserById(id);
        User otherUser = getUserById(otherId);
        if (user.getFriends().isEmpty() || otherUser.getFriends().isEmpty()) {
            log.info("У User с id = ", id, "нет общих друзей с User c id = ", otherId);
            return new ArrayList<>();
        } else {
            log.info("Вывод общих друзей User с id = ", id, "и User c id = ", otherId);
            List listUsersFriend = getOfAllUsersFriends(id);
            List listOtherUsersFriend = getOfAllUsersFriends(otherId);
            listUsersFriend.retainAll(listOtherUsersFriend);
            return listUsersFriend;
        }
    }
}
