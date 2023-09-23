package com.musicpedia.musicpediaapi.global.exception;

public class ExpiredAccessTokenException extends RuntimeException{
    public ExpiredAccessTokenException(String message){
        super(message);
    }
}