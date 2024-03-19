package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.controller.exception.ExceptionHandler;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import com.tratsiak.englishwords.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trainingsRusToEng")
public class TrainingRusToEngController {

    private final TrainingTranslateWordService trainingTranslateWordRusToEngService;
    private final ExceptionHandler exceptionHandler;
    @Autowired
    public TrainingRusToEngController(TrainingTranslateWordService trainingTranslateWordRusToEngService,
                                      ExceptionHandler exceptionHandler) {
        this.trainingTranslateWordRusToEngService = trainingTranslateWordRusToEngService;
        this.exceptionHandler = exceptionHandler;
    }

    @GetMapping
    @JsonView(View.TrainingRusToEng.class)
    private TrainingTranslateWordRusToEng get(Authentication authentication, @RequestParam boolean isLearned) {
        try {
            long userId = (Long) authentication.getPrincipal();
            return (TrainingTranslateWordRusToEng) trainingTranslateWordRusToEngService.get(userId, isLearned);
        } catch (ServiceException e) {
            throw exceptionHandler.handle(e);
        }
    }

    @PostMapping
    private long answer(Authentication authentication, @RequestBody TrainingTranslateWordRusToEng rusToEng) {
        try {
            long userId = (Long) authentication.getPrincipal();
            return trainingTranslateWordRusToEngService.checkAnswer(userId, rusToEng);
        } catch (ServiceException e) {
            throw exceptionHandler.handle(e);
        }
    }
}
