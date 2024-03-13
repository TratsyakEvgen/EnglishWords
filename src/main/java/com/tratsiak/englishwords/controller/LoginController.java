package com.tratsiak.englishwords.controller;

import com.tratsiak.englishwords.model.bean.Auth;
import com.tratsiak.englishwords.model.bean.Token;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final TokenService tokenService;

    @Autowired
    public LoginController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping
    private Token getTokenForTelegram(@RequestBody Auth auth) {
        try {
            return tokenService.get(auth);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage(), e);
        }
    }
}
