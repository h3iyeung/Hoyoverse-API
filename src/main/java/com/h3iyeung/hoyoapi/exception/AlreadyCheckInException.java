package com.h3iyeung.hoyoapi.exception;

public class AlreadyCheckInException extends HoyoverseAPIRetCodeException{
    public AlreadyCheckInException(int retcode, String message) {
        super(retcode, message);
    }
}
