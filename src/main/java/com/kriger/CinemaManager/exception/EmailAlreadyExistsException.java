package com.kriger.CinemaManager.exception;

public class EmailAlreadyExistsException extends RuntimeException {

    public EmailAlreadyExistsException(String email) {
        super("Пользователь с таким email: " + email + " уже существует");
    }
}