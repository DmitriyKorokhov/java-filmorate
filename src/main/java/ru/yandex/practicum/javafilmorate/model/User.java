package ru.yandex.practicum.javafilmorate.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@Builder
public class User {
    private int id;
    @Email(message = "Email неправильного формата")
    @NotEmpty(message = "Email не может быть пустым")
    private String email;
    @Pattern(regexp = "^\\S*", message = "Login не должен содержать пробелы")
    @NotBlank(message = "Login не может быть пустым")
    private String login;
    private String name;
    @PastOrPresent(message = "Birthday не может быть позже сегодняшнего дня")
    private LocalDate birthday;
}