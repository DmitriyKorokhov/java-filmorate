package ru.yandex.practicum.javafilmorate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class User {
    private Integer id;
    @Email(message = "Email неправильного формата")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;
    @Pattern(regexp = "^\\S*", message = "Login не должен содержать пробелы")
    @NotBlank(message = "Login не может быть пустым")
    private String login;
    private String name;
    @PastOrPresent(message = "Birthday не может быть позже сегодняшнего дня")
    private LocalDate birthday;

    public User(String login, String name, String email, LocalDate birthday) {
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }

    public User(Integer id, String login, String name, String email, LocalDate birthday) {
        this.id = id;
        this.login = login;
        this.name = name;
        this.email = email;
        this.birthday = birthday;
    }
}