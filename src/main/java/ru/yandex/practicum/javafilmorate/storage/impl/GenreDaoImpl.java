package ru.yandex.practicum.javafilmorate.storage.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Genre;
import ru.yandex.practicum.javafilmorate.storage.GenreDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void addFilmGenre(Film film) {
        if (film.getGenres() == null || film.getGenres().isEmpty()) {
            return;
        }
        String sql = "INSERT INTO film_genre (film_id, genre_id) " + "VALUES(?,?)";
        List<Genre> genres = new ArrayList<>(film.getGenres());
        jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, film.getId());
                ps.setLong(2, genres.get(i).getId());
            }

            @Override
            public int getBatchSize() {
                return film.getGenres().size();
            }
        });
    }

    @Override
    public Genre getGenreById(int id) {
        String sqlQuery = "SELECT * FROM genres WHERE id = ?";
        return jdbcTemplate.queryForObject(sqlQuery, this::mapRowToGenre, id);
    }

    @Override
    public List<Genre> getAllGenres() {
        String sqlQuery = "SELECT * FROM genres";
        return jdbcTemplate.query(sqlQuery, this::mapRowToGenre);
    }

    @Override
    public void updateFilmGenre(Film film) {
        String sqlQueryGenres = "DELETE FROM film_genre WHERE film_id = ?";
        jdbcTemplate.update(sqlQueryGenres, film.getId());
        addFilmGenre(film);
    }

    @Override
    public boolean isGenreExistedById(int id) {
        String sqlQuery = "SELECT name FROM genres WHERE id = ?";
        return jdbcTemplate.queryForRowSet(sqlQuery, id).next();
    }

    @Override
    public List<Film> loadGenres(List<Film> films) {
        final Map<Integer, Film> ids = films.stream().collect(Collectors.toMap(Film::getId, Function.identity()));
        String inSql = String.join(",", Collections.nCopies(films.size(), "?"));
        final String sqlQuery = "SELECT * FROM genres g, film_genre fg " +
                "WHERE fg.genre_id = g.id AND fg.film_id IN (" + inSql + ")";
        jdbcTemplate.query(sqlQuery, (rs) -> {
            if (!rs.wasNull()) {
                final Film film = ids.get(rs.getInt("FILM_ID"));
                film.addGenre(Genre.builder().id(rs.getInt("ID")).name(rs.getString("NAME")).build());
            }
        }, films.stream().map(Film::getId).toArray());
        return new ArrayList<>(ids.values());
    }

    public Genre mapRowToGenre(ResultSet rs, int rowNum) throws SQLException {
        return Genre.builder().id(rs.getInt("id")).name(rs.getString("name")).build();
    }
}