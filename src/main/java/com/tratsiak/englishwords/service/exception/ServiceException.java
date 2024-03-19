package com.tratsiak.englishwords.service.exception;

import lombok.Getter;

@Getter
public class ServiceException extends Exception {

    private final LevelException levelException;
    private final String publicMessage;


    public ServiceException(LevelException levelException, String publicMessage, String message) {
        super(message);
        this.levelException = levelException;
        this.publicMessage = publicMessage;
    }

    public ServiceException(LevelException levelException, String publicMessage, String message, Throwable cause) {
        super(message, cause);
        this.levelException = levelException;
        this.publicMessage = publicMessage;
    }


}
