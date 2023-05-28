package ru.yandex.practicum.javafilmorate.storage.impl;

import lombok.AllArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.LikesDao;

@Component
@AllArgsConstructor
public class LikesDaoImpl implements LikesDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addLike(int id, int userId) {
        String sqlQuery = "INSERT INTO likes (film_id, user_id) VALUES (?,?)";
        jdbcTemplate.update(sqlQuery, id, userId);
    }

    @Override
    public void deleteLike(int id, int userId) {
        String sqlQuery = "DELETE FROM likes WHERE film_id = ? AND user_id = ?";
        jdbcTemplate.update(sqlQuery, id, userId);
    }
}