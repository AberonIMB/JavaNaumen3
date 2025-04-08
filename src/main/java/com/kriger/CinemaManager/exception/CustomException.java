package com.kriger.CinemaManager.exception;

public class CustomException {

    private String message;
    private int statusCode;

    public CustomException(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public static CustomException create(String message, int statusCode) {
        return new CustomException(message, statusCode);
    }
}