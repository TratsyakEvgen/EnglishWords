package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordEngToRus;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainingsEngToRus")
public class TrainingEngToRusController {

    private final TrainingTranslateWordService trainingTranslateWordEngToRusService;

    @Autowired
    public TrainingEngToRusController(TrainingTranslateWordService trainingTranslateWordEngToRusService) {

        this.trainingTranslateWordEngToRusService = trainingTranslateWordEngToRusService;
    }

    @GetMapping
    @JsonView(View.TrainingEngToRus.class)
    private TrainingTranslateWordEngToRus get(Authentication authentication, @RequestParam boolean isLearned)
            throws ServiceException {

        long userId = (Long) authentication.getPrincipal();
        return (TrainingTranslateWordEngToRus) trainingTranslateWordEngToRusService.get(userId, isLearned);
    }

    @PostMapping
    private long answer(Authentication authentication, @RequestBody TrainingTranslateWordEngToRus engToRus)
            throws ServiceException {

        long userId = (Long) authentication.getPrincipal();
        return trainingTranslateWordEngToRusService.checkAnswer(userId, engToRus);
    }
}