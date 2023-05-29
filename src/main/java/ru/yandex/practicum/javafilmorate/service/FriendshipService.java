package ru.yandex.practicum.javafilmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.storage.FriendshipDao;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class FriendshipService {

    private final FriendshipDao friendshipDao;
    private final UserService userService;

    public void addFriend(int id, int friendId) {
        userService.checkUserExist(id);
        userService.checkUserExist(friendId);
        log.info("User с id = " + friendId + " добавлен в список друзей User c id = " + id);
        friendshipDao.addFriend(id, friendId);
    }

    public void deleteFriendById(int id, int friendId) {
        userService.checkUserExist(id);
        userService.checkUserExist(friendId);
        log.info("User с id = " + friendId + " удален из списка друзей у User c id = " + id);
        friendshipDao.deleteFriend(id, friendId);
    }

    public List<User> getOfAllUsersFriends(int id) {
        List<User> friendsList = new ArrayList<>(friendshipDao.getAllFriends(id));
        log.info("Вывод всех друзей User с id = " + id);
        return friendsList;
    }

    public List<User> getCommonFriends(int id, int otherId) {
        userService.checkUserExist(id);
        userService.checkUserExist(otherId);
        List<User> listUsersFriend = getOfAllUsersFriends(id);
        List<User> listOtherUsersFriend = getOfAllUsersFriends(otherId);
        listUsersFriend.retainAll(listOtherUsersFriend);
        log.info("Список друзей User с id = " + id + " общих с User, id которого " + otherId);
        return listUsersFriend;
    }
}
