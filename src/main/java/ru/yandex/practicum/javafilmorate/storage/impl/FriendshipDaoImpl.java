package ru.yandex.practicum.javafilmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.FriendshipDao;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.List;

@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;
    private final UserDaoImpl userDao;

    @Override
    public void addFriend(int id, int friendId) {
        String sqlQuery = "INSERT INTO friendship (user_id,friend_user_id) VALUES (?,?)";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public void deleteFriend(int id, int friendId) {
        String sqlQuery = "DELETE FROM friendship WHERE user_id = ? AND friend_user_id = ?";
        jdbcTemplate.update(sqlQuery, id, friendId);
    }

    @Override
    public List<User> getCommonFriends(int id, int otherId) {
        String sqlQuery = "SELECT users.* FROM users " +
                "JOIN FRIENDSHIP AS f1 on(users.id = f1.friend_id AND f1.user_id = ?) " +
                "JOIN FRIENDSHIP AS f2 on (users.id = f2.friend_id AND f2.user_id =?)";
        return jdbcTemplate.query(sqlQuery, userDao::mapRowToUser, id, otherId);
    }

    @Override
    public List<User> getAllFriends(int id) {
        String sqlQuery = "SELECT * FROM users " +
                "WHERE id IN (SELECT friend_user_id AS id FROM friendship WHERE user_id = ?)";
        return jdbcTemplate.query(sqlQuery, userDao::mapRowToUser, id);
    }
}
