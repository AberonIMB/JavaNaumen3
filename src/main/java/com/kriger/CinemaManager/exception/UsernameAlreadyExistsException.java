package com.kriger.CinemaManager.exception;

public class UsernameAlreadyExistsException extends RuntimeException {

    public UsernameAlreadyExistsException(String username) {
        super("Пользователь с таким username: " + username + " уже существует");
    }
}