package ru.yandex.practicum.javafilmorate.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.boot.test.context.SpringBootTest;

import ru.yandex.practicum.javafilmorate.model.User;

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
        userController = new UserController();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }


    @Test
    void addUser() {
        user = new User(1, "test@mail.ru", "testLogin", "Dmitriy",
                LocalDate.of(2003, 11, 24));
        userController.addUser(user);
        List<User> savedUsers = userController.getUsers();
        assertEquals(1, savedUsers.size(), "Неверное количество users");
        assertEquals(user, savedUsers.get(0), "User некорректно добавлен");
    }

    @Test
    void notAddUserWithNullEmail() {
        user = new User(1, null, "testLogin", "Dmitriy",
                LocalDate.of(2003, 11, 24));
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
        user = new User(1, "mail.ru", "testLogin", "Dmitriy",
                LocalDate.of(2003, 11, 24));
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
        user = new User(1, "test@mail.ru", "", "Dmitriy",
                LocalDate.of(2003, 11, 24));
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
        user = new User(1, "test@mail.ru", "test login", "Dmitriy",
                LocalDate.of(2003, 11, 24));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("login: Login не должен содержать пробелы");
            }
        });
        Assertions.assertEquals("login: Login не должен содержать пробелы", exception.getMessage());
    }

    @Test
    void notAddUserWithNullBirthday() {
        user = new User(1, "test@mail.ru", "testLogin", "Dmitriy", null);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> violations = validator.validate(user);
            if (!violations.isEmpty()) {
                throw new ValidationException("birthday: Birthday не должен быть пустым");
            }
        });
        Assertions.assertEquals("birthday: Birthday не должен быть пустым", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectBirthday() {
        user = new User(1, "test@mail.ru", "testLogin", "Dmitriy",
                LocalDate.of(2199, 11, 24));
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
        user = new User(1, "test@mail.ru", "testLogin", "",
                LocalDate.of(2003, 11, 24));
        userController.addUser(user);
        List<User> savedUsers = userController.getUsers();
        assertEquals(1, savedUsers.size(), "Неверное количество users");
        assertEquals(user.getLogin(), savedUsers.get(0).getName(), "User's name добавлено некорректно");
    }

    @Test
    void updateUser() {
        user = new User(1, "test@mail.ru", "testLogin", "Dmitriy",
                LocalDate.of(2003, 11, 24));
        userController.addUser(user);
        User newUser = new User(1, "test@mail.ru", "testLogin", "Kate",
                LocalDate.of(2004, Month.APRIL, 13));
        userController.updateUser(newUser);
        List<User> savedUsers = userController.getUsers();
        assertEquals(1, savedUsers.size(), "Неверное количество users");
        assertEquals(newUser, savedUsers.get(0), "User's email isn't correct");
    }
}