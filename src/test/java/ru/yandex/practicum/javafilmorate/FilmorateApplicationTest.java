package ru.yandex.practicum.javafilmorate;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.yandex.practicum.javafilmorate.model.Film;
import ru.yandex.practicum.javafilmorate.model.Mpa;
import ru.yandex.practicum.javafilmorate.storage.*;
import ru.yandex.practicum.javafilmorate.model.User;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class FilmorateApplicationTest {

    private final UserDao userDao;
    private final FilmDao filmDao;
    private final FriendshipDao friendshipDao;
    private final GenreDao genreDao;
    private final LikesDao likesDao;
    private final MpaDao mpaDao;

    @Test
    void addUserTest() {
        userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        Optional<User> userOptional = Optional.of(userDao.getUserById(1));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("email", "testUser@ya.ru")
                                .hasFieldOrPropertyWithValue("login", "TestLogin")
                                .hasFieldOrPropertyWithValue("name", "TestName")
                                .hasFieldOrPropertyWithValue("birthday",
                                        LocalDate.of(2000, 1, 1))
                );
    }

    @Test
    void updateUserTest() {
        User userForUpdate = userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        userForUpdate.setEmail("updatedEmail");
        userForUpdate.setLogin("UpdatedLogin");
        Optional<User> userOptional = Optional.of(userDao.updateUser(userForUpdate));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("email", "updatedEmail")
                                .hasFieldOrPropertyWithValue("login", "UpdatedLogin")
                                .hasFieldOrPropertyWithValue("name", "TestName")
                                .hasFieldOrPropertyWithValue("birthday",
                                        LocalDate.of(2000, 1, 1))
                );
    }

    @Test
    void getUserByIdTest() {
        User newUser = userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        Optional<User> userOptional = Optional.of(userDao.getUserById(newUser.getId()));
        assertThat(userOptional)
                .isPresent()
                .hasValueSatisfying(user ->
                        assertThat(user)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("email", "testUser@ya.ru")
                                .hasFieldOrPropertyWithValue("login", "TestLogin")
                                .hasFieldOrPropertyWithValue("name", "TestName")
                                .hasFieldOrPropertyWithValue("birthday",
                                        LocalDate.of(2000, 1, 1))
                );
    }

    @Test
    void getAllUsersTest() {
        userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        userDao.addUser(new User(2, "otherTestLogin", "otherTetName", "othertetUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        List<User> users = userDao.getAllUsers();
        assertThat(users).size()
                .isEqualTo(2);
    }

    @Test
    void addFilmTest() {
        filmDao.addFilm(new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2,
                new Mpa(1, "R"), new LinkedHashSet<>()));
        Optional<Film> filmOptional = Optional.of(filmDao.getFilmById(1));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "The Batman")
                                .hasFieldOrPropertyWithValue("description", "Batman will have to " +
                                        "distinguish friend from foe " +
                                        "and restore justice in the name of Gotham.")
                                .hasFieldOrPropertyWithValue("duration", 2)
                                .hasFieldOrPropertyWithValue("releaseDate",
                                        LocalDate.of(2022, Month.MARCH, 1))
                );
    }

    @Test
    void updateFilmTest() {
        filmDao.addFilm(new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2,
                new Mpa(1, "R"), new LinkedHashSet<>()));
        Film newfilm = filmDao.getFilmById(1);
        newfilm.setName("UpdatedFilm");
        newfilm.setDescription("UpdatedDescription");
        Optional<Film> filmOptional = Optional.of(filmDao.updateFilm(newfilm));
        assertThat(filmOptional)
                .isPresent()
                .hasValueSatisfying(film ->
                        assertThat(film)
                                .hasFieldOrPropertyWithValue("id", 1)
                                .hasFieldOrPropertyWithValue("name", "UpdatedFilm")
                                .hasFieldOrPropertyWithValue("description", "UpdatedDescription")
                                .hasFieldOrPropertyWithValue("duration", 2)
                                .hasFieldOrPropertyWithValue("releaseDate",
                                        LocalDate.of(2022, Month.MARCH, 1))
                );
    }

    @Test
    void getFilmsListTest() {
        filmDao.addFilm(new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2,
                new Mpa(1, "R"), new LinkedHashSet<>()));
        filmDao.addFilm(new Film(2, "Spider-Man", "Peter becomes a real superhero named Spider-Man, " +
                "who helps people and fights crime. But where there is a superhero, " +
                "sooner or later a supervillain always appears.",
                LocalDate.of(2022, Month.MARCH, 1), 2,
                new Mpa(1, "R"), new LinkedHashSet<>()));
        List<Film> films = filmDao.getAllFilms();
        assertThat(films).size()
                .isEqualTo(2);
    }

    @Test
    void addLikeToFilmTest() {
        filmDao.addFilm(new Film(1, "The Batman", "Batman will have to distinguish friend from foe " +
                "and restore justice in the name of Gotham.",
                LocalDate.of(2022, Month.MARCH, 1), 2,
                new Mpa(1, "R"), new LinkedHashSet<>()));
        userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        Film film = filmDao.getFilmById(1);
        User user = userDao.getUserById(1);
        likesDao.addLike(film.getId(), user.getId());
        List<Film> topFilmsAfterLike = new ArrayList<>(filmDao.listOfFilmsByNumberOfLikes(1));
        assertThat(topFilmsAfterLike.get(0))
                .hasFieldOrPropertyWithValue("name", "The Batman")
                .hasFieldOrPropertyWithValue("description", "Batman will have to " +
                        "distinguish friend from foe " +
                        "and restore justice in the name of Gotham.");
    }

    @Test
    void testAddFriend() {
        userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        userDao.addUser(new User(2, "otherTestLogin", "otherTetName", "othertetUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        friendshipDao.addFriend(1, 2);
        Optional<List<User>> friendsUserOne = Optional.of(friendshipDao.getAllFriends(1));
        assertEquals(friendsUserOne.get().size(), 1);
        assertThat(friendsUserOne)
                .isPresent()
                .hasValueSatisfying(list -> {
                    assertThat(list.get(0)).hasFieldOrPropertyWithValue("id", 2);
                });
    }

    @Test
    void testRemoveFriend() {
        userDao.addUser(new User(1, "TestLogin", "TestName", "testUser@ya.ru",
                LocalDate.of(2000, 1, 1)));

        userDao.addUser(new User(2, "otherTestLogin", "otherTetName", "othertetUser@ya.ru",
                LocalDate.of(2000, 1, 1)));
        friendshipDao.addFriend(1, 2);
        friendshipDao.deleteFriend(1, 2);
        Optional<List<User>> friendsUserOne = Optional.of(friendshipDao.getAllFriends(1));
        assertTrue(friendsUserOne.isPresent());
        assertTrue(friendsUserOne.get().isEmpty());
    }

    @Test
    public void testGetGenres() {
        assertEquals(genreDao.getAllGenres().size(), 6, "Неверное количество Genres");
    }

    @Test
    public void testGetGenreById() {
        assertEquals(genreDao.getGenreById(1).getName(), "Комедия", "Неверный жанр");
        assertEquals(genreDao.getGenreById(3).getName(), "Мультфильм", "Неверный жанр");
        assertEquals(genreDao.getGenreById(6).getName(), "Боевик", "Неверный жанр");
    }

    @Test
    void testGetMpas() {
        assertEquals(mpaDao.getAllMpa().size(), 5, "Неверное количество Mpa");
    }

    @Test
    void getMpaByIdTest() {
        Optional<Mpa> mpa = Optional.of(mpaDao.getMpaById(1));
        assertThat(mpa)
                .isPresent()
                .hasValueSatisfying(mpaRating ->
                        assertThat(mpaRating)
                                .hasFieldOrPropertyWithValue("name", "G")
                );
    }
}