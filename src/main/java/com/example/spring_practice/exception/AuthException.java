package com.example.spring_practice.exception;

public class AuthException extends ApiException{

    public AuthException(String message, String errorCode) {
        super(message, errorCode);
    }
}
