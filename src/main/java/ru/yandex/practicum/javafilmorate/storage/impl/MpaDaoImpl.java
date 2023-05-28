package ru.yandex.practicum.javafilmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.storage.MpaDao;
import ru.yandex.practicum.javafilmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MpaDaoImpl implements MpaDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public Mpa getMpaById(int id) {
        String sqlQuery = "SELECT * FROM rating_mpa WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToMpa, id);
    }

    @Override
    public List<Mpa> getAllMpa() {
        String sqlQuery = "SELECT * FROM rating_mpa";
        return jdbcTemplate.query(sqlQuery, this::mapRowToMpa);
    }

    @Override
    public boolean isMpaExistedById(int id) {
        String sqlQuery = "SELECT name FROM rating_mpa WHERE id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }

    private Mpa mapRowToMpa(ResultSet rs, int rowNum) throws SQLException {
        return Mpa.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
    }
}