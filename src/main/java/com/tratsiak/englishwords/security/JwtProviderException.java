package com.tratsiak.englishwords.security;

public class JwtProviderException extends Exception {
    public JwtProviderException() {
    }

    public JwtProviderException(String message) {
        super(message);
    }

    public JwtProviderException(String message, Throwable cause) {
        super(message, cause);
    }

    public JwtProviderException(Throwable cause) {
        super(cause);
    }

    public JwtProviderException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
