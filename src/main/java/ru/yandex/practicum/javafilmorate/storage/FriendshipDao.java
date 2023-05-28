package ru.yandex.practicum.javafilmorate.storage;

import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

public interface FriendshipDao {
    void addFriend(int id, int friendId);

    void deleteFriend(int id, int friendId);

    List<User> getCommonFriends(int id, int otherId);

    List<User> getAllFriends(int id);
}
