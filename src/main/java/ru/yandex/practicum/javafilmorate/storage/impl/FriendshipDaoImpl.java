package ru.yandex.practicum.javafilmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.FriendshipDao;
import ru.yandex.practicum.javafilmorate.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FriendshipDaoImpl implements FriendshipDao {

    private final JdbcTemplate jdbcTemplate;

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
    public List<User> getCommonFriends(int id, int friendId) {
        String sqlQuery = "SELECT * FROM users " +
                "WHERE id IN (SELECT friend_user_id FROM friendship WHERE user_id = ?) " +
                "AND id IN (SELECT friend_user_id FROM friendship WHERE user_id = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, id, friendId);
        List<User> commonFriends = new ArrayList<>();
        while (rs.next()) {
            commonFriends.add(User.builder()
                            .id(rs.getInt("id"))
                            .name(rs.getString("login"))
                            .login(rs.getString("name"))
                            .email(rs.getString("email"))
                            .birthday(Objects.requireNonNull(rs.getDate("birthday")).toLocalDate())
                    .build());
        }
        return commonFriends.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<User> getAllFriends(int id) {
        String sqlQuery = "SELECT * FROM users WHERE id IN " +
                "(SELECT friend_user_id AS id FROM friendship WHERE user_id = ?)";
        SqlRowSet rs = jdbcTemplate.queryForRowSet(sqlQuery, id);
        List<User> friends = new ArrayList<>();
        while (rs.next()) {
            friends.add(User.builder()
                    .id(rs.getInt("id"))
                    .name(rs.getString("login"))
                    .login(rs.getString("name"))
                    .email(rs.getString("email"))
                    .birthday(Objects.requireNonNull(rs.getDate("birthday")).toLocalDate())
                    .build());
        }
        return friends;
    }
}
