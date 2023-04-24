package ru.yandex.practicum.javafilmorate.model;

import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
public class User {

    private int id;
    @Email(message = "Email неправильного формата")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;
    @Pattern(regexp = "^\\S*", message = "Login не должен содержать пробелы")
    @NotBlank(message = "Login не может быть пустым")
    private String login;
    private String name;
    @NotNull(message = "Birthday не должен быть пустым")
    @PastOrPresent(message = "Birthday не может быть позже сегодняшнего дня")
    private LocalDate birthday;

    public User(int id, String email, String login, String name, LocalDate birthday) {
        this.id = id;
        this.email = email;
        this.login = login;
        this.name = name;
        this.birthday = birthday;
    }
}