package com.h3iyeung.hoyoapi.exception;

public class InvalidParameterException extends HoyoverseAPIRetCodeException {
    public InvalidParameterException(int retcode, String message) {
        super(retcode, message);
    }
}
