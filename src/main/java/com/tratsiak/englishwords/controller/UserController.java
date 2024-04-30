package com.tratsiak.englishwords.controller;

import com.tratsiak.englishwords.model.bean.Registration;
import com.tratsiak.englishwords.service.UserService;
import com.tratsiak.englishwords.service.exception.ServiceException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {

        this.userService = userService;
    }

    @PostMapping
    private void registration(@RequestBody @Valid Registration registration) throws ServiceException {

        userService.register(registration);
    }
}
