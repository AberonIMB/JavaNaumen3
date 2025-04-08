package com.kriger.CinemaManager.exception;

import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExceptionControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<CustomException> handleException(Exception e) {
        CustomException customException = new CustomException(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(customException, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<CustomException> handleResourceNotFoundException(ResourceNotFoundException e) {
        CustomException customException = new CustomException(e.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(customException, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomException> handleIllegalArgumentException(IllegalArgumentException e) {
        CustomException customException = new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<CustomException> handleIllegalStateException(IllegalStateException e) {
        CustomException customException = new CustomException(e.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(customException, HttpStatus.BAD_REQUEST);
    }
}