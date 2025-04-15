package com.kriger.CinemaManager.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserRegisterDTO {

    @NotBlank(message = "Имя не должно быть пустым")
    private String username;

    @Email(message = "Некорректный email")
    private String email;

    @Size(min = 6, max = 32, message = "Длина пароля должна быть от {min} до {max} символов")
    private String password;

    public UserRegisterDTO(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public UserRegisterDTO() { }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
