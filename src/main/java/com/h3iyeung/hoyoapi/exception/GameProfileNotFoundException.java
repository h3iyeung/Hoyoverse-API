package com.h3iyeung.hoyoapi.exception;

public class GameProfileNotFoundException extends HoyoverseAPIRetCodeException{
    public GameProfileNotFoundException(int retcode, String message) {
        super(retcode, message);
    }
}
