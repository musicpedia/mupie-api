package com.musicpedia.musicpediaapi.global.exception;

public class TokenNotFoundException extends RuntimeException {

    public TokenNotFoundException(String message) {
        super(message);
    }
}