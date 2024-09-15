package com.h3iyeung.hoyoapi.exception;

public class HoyoverseLoginFailedException extends HoyoverseAPIRetCodeException{
    public HoyoverseLoginFailedException(int retcode, String message) {
        super(retcode, message);
    }
}
