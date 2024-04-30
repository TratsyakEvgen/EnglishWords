package com.tratsiak.englishwords.controller;

import com.tratsiak.englishwords.model.bean.Auth;
import com.tratsiak.englishwords.model.bean.AuthTelegramApp;
import com.tratsiak.englishwords.model.bean.Token;
import com.tratsiak.englishwords.service.TokenService;
import com.tratsiak.englishwords.service.exception.ServiceException;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tokens")
public class TokenController {

    private final TokenService tokenService;

    @Autowired
    public TokenController(TokenService tokenService) {

        this.tokenService = tokenService;
    }

    @Operation(summary = "Авторизация")


    @PostMapping("/login")
    private Token getToken(@RequestBody @Valid Auth auth) throws ServiceException {

        return tokenService.get(auth);
    }


    @PostMapping
    private Token getTokenForTelegram(@RequestBody AuthTelegramApp auth) throws ServiceException {

        return tokenService.get(auth);
    }


    @PutMapping
    private Token getNewToken(@RequestBody Token token) throws ServiceException {

        return tokenService.get(token);
    }
}