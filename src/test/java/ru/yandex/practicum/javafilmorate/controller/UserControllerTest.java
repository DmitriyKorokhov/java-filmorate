package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.javafilmorate.model.User;
import ru.yandex.practicum.javafilmorate.service.UserService;
import ru.yandex.practicum.javafilmorate.storage.InMemoryUserStorage;

import javax.validation.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserControllerTest {
    private static UserController userController;
    private User user;
    private Validator validator;

    @BeforeEach
    void beforeEach() {
        userController = new UserController(new UserService(new InMemoryUserStorage()));
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void addUser() {
        user = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("testLogin")
                .name("Dmitriy")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        userController.addUser(user);
        List<User> savedUsers = userController.getAllUsers();
        assertEquals(1, savedUsers.size(), "Неверное количество users");
        assertEquals(user, savedUsers.get(0), "User некорректно добавлен");
    }

    @Test
    void notAddUserWithNullEmail() {
        user = User.builder()
                .id(1)
                .email(null)
                .login("testLogin")
                .name("Dmitriy")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("email: Email не может быть пустым");
            }
        });
        Assertions.assertEquals("email: Email не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectEmail() {
        user = User.builder()
                .id(1)
                .email("mail.ru")
                .login("testLogin")
                .name("Dmitriy")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("email: Email неправильного формата");
            }
        });
        Assertions.assertEquals("email: Email неправильного формата", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectLogin() {
        user = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("")
                .name("Dmitriy")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("login: Login не может быть пустым");
            }
        });
        Assertions.assertEquals("login: Login не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddUserLoginWithASpace() {
        user = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("tes tLogin")
                .name("Dmitriy")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("login: Login не должен содержать пробелы");
            }
        });
        Assertions.assertEquals("login: Login не должен содержать пробелы", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectBirthday() {
        user = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("testLogin")
                .name("Dmitriy")
                .birthday(LocalDate.of(2199, 11, 24))
                .build();
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            user.setBirthday(LocalDate.of(2895, 12, 28));
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("birthday: Birthday не может быть позже сегодняшнего дня");
            }
        });
        Assertions.assertEquals("birthday: Birthday не может быть позже сегодняшнего дня", exception.getMessage());
    }

    @Test
    void addUserWithEmptyName() {
        user = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("testLogin")
                .name("")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        userController.addUser(user);
        List<User> savedUsers = userController.getAllUsers();
        assertEquals(1, savedUsers.size(), "Неверное количество users");
        assertEquals(user.getLogin(), savedUsers.get(0).getName(), "User's name добавлено некорректно");
    }

    @Test
    void updateUser() {
        user = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("testLogin")
                .name("Dmitriy")
                .birthday(LocalDate.of(2003, 11, 24))
                .build();
        userController.addUser(user);
        User newUser = User.builder()
                .id(1)
                .email("test@mail.ru")
                .login("testLogin")
                .name("Kate")
                .birthday(LocalDate.of(2004, Month.APRIL, 13))
                .build();
        userController.updateUser(newUser);
        List<User> savedUsers = userController.getAllUsers();
        assertEquals(1, savedUsers.size(), "Неверное количество users");
        assertEquals(newUser, savedUsers.get(0), "User's email isn't correct");
    }
}