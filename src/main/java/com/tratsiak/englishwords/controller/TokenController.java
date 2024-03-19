package com.tratsiak.englishwords.controller;

import com.tratsiak.englishwords.controller.exception.ExceptionHandler;
import com.tratsiak.englishwords.model.bean.AuthTelegramApp;
import com.tratsiak.englishwords.model.bean.Token;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;
    private final ExceptionHandler exceptionHandler;

    @Autowired
    public TokenController(TokenService tokenService, ExceptionHandler exceptionHandler) {
        this.tokenService = tokenService;
        this.exceptionHandler = exceptionHandler;
    }

    @PostMapping
    private Token getTokenForTelegram(@RequestBody AuthTelegramApp auth) {
        try {
            return tokenService.get(auth);
        } catch (ServiceException e) {
            throw exceptionHandler.handle(e);
        }
    }

    @PutMapping
    private Token getNewToken(@RequestBody Token token) {
        try {
            return tokenService.get(token);
        } catch (ServiceException e) {
            throw exceptionHandler.handle(e);
        }
    }
}
