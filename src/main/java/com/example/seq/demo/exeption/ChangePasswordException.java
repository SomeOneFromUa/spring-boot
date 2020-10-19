package com.example.seq.demo.exeption;

public class ChangePasswordException extends RuntimeException {
    public ChangePasswordException(String message) {
        super(message);
    }
}
