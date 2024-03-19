package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.controller.exception.ExceptionHandler;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordEngToRus;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import com.tratsiak.englishwords.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trainingsEngToRus")
public class TrainingEngToRusController {


    private final TrainingTranslateWordService trainingTranslateWordEngToRusService;
    private final ExceptionHandler exceptionHandler;

    @Autowired
    public TrainingEngToRusController(TrainingTranslateWordService trainingTranslateWordEngToRusService,
                                      ExceptionHandler exceptionHandler) {
        this.trainingTranslateWordEngToRusService = trainingTranslateWordEngToRusService;
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping
    @JsonView(View.TrainingEngToRus.class)
    private TrainingTranslateWordEngToRus get(Authentication authentication, @RequestParam boolean isLearned) {
        try {
            long userId = (Long) authentication.getPrincipal();
            return (TrainingTranslateWordEngToRus) trainingTranslateWordEngToRusService.get(userId, isLearned);
        } catch (ServiceException e) {
            throw exceptionHandler.handle(e);
        }
    }

    @PostMapping
    private long answer(Authentication authentication, @RequestBody TrainingTranslateWordEngToRus engToRus) {
        try {
            long userId = (Long) authentication.getPrincipal();
            return trainingTranslateWordEngToRusService.checkAnswer(userId, engToRus);
        } catch (ServiceException e) {
            throw exceptionHandler.handle(e);
        }
    }
}

