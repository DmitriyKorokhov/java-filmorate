package ru.yandex.practicum.filmorate.controllers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    public void addUser() {
        user = new User(1, "test@mail.ru", "login", "Dmitriy",
                LocalDate.of(2003, 7, 8));
        userController.addUser(user);
        List<User> savedUsers = userController.getUsers();
        assertEquals(user, savedUsers.get(0), "User добавлен некорректно");
        assertEquals(1, savedUsers.size(), "Неверное количество Users");
    }

    @Test
    void shouldCreateUserWithEmptyName() {
        user = new User(1, "test@mail.ru", "login", "",
                LocalDate.of(2003, 7, 8));
        userController.addUser(user);
        List<User> savedUsers = userController.getUsers();
        assertEquals(user.getLogin(), savedUsers.get(0).getName(), "User добавлен некорректно");
        assertEquals(1, savedUsers.size(), "Неверное количество Users");
    }

    @Test
    public void notAddUserWithNullEmail() {
        user = new User(1, null, "login", "Dmitriy",
                LocalDate.of(2002, 2, 2));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("email: Email не действителен");
            }
        });
        Assertions.assertEquals("email: Email не действителен", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectEmail() {
        user = new User(1, "test.mail.ru", "login", "Dmitriy",
                LocalDate.of(1999, 5, 8));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("email: Email не действителен");
            }
        });
        Assertions.assertEquals("email: Email не действителен", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectLogin() {
        user = new User(1, "test@mail.ru", "", "Dmitriy",
                LocalDate.of(2003, 10, 8));
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("login: Login не может быть пустым");
            }
        });
        Assertions.assertEquals("login: Login не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddUserWithNullBirthday() {
        user = new User(1, "test.mail.ru", "login", "Dmitriy", null);
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("birthday: Birthday не может быть пустым");
            }
        });
        Assertions.assertEquals("birthday: Birthday не может быть пустым", exception.getMessage());
    }

    @Test
    void notAddUserWithIncorrectBirthday() {
        ValidationException exception = Assertions.assertThrows(ValidationException.class, () -> {
            user = new User(1, "test.mail.ru", "login", "Dmitriy",
                    LocalDate.of(2530, 11, 24));
            Set<ConstraintViolation<User>> constraintViolations = validator.validate(user);
            if (!constraintViolations.isEmpty()) {
                throw new ValidationException("birthday: Birthday не может быть позже чем настоящая дата");
            }
        });
        Assertions.assertEquals("birthday: Birthday не может быть позже чем настоящая дата", exception.getMessage());
    }

    @Test
    void updateUser() {
        user = new User(1, "test@mail.ru", "login", "Dmitriy",
                LocalDate.of(2003, 7, 8));
        userController.addUser(user);
        User newUser = new User(1, "emailForUpdate@mail.ru", "loginForUpdate", "Kate",
                LocalDate.of(2004, Month.APRIL, 13));
        userController.updateUser(newUser);
        List<User> savedUsers = userController.getUsers();
        assertEquals(newUser, savedUsers.get(0), "User обновлен некорректно");
        assertEquals(1, savedUsers.size(), "Неверное количество Users");
    }

    @Test
    void updateUserWithEmptyName() {
        user = new User(1, "test@mail.ru", "login", "Dmitriy",
                LocalDate.of(2003, 7, 8));
        userController.addUser(user);
        User newUser = new User(1, "emailForUpdate@mail.ru", "loginForUpdate", "",
                LocalDate.of(2004, Month.APRIL, 13));
        userController.updateUser(newUser);
        List<User> savedUsers = userController.getUsers();
        assertEquals(newUser.getLogin(), savedUsers.get(0).getName(), "User обновлен некорректно");
        assertEquals(1, savedUsers.size(), "Неверное количество Users");
    }
}