package com.musicpedia.musicpediaapi.global.exception;

public class ExpiredRefreshTokenException extends RuntimeException{
    public ExpiredRefreshTokenException(String message){
        super(message);
    }
}