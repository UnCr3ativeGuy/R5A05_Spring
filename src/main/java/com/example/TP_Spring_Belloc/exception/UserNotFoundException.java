package com.example.TP_Spring_Belloc.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user " + id);
    }
}