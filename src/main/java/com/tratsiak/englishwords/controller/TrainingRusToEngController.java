package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/trainingsRusToEng")
public class TrainingRusToEngController {

    private final TrainingTranslateWordService trainingTranslateWordRusToEngService;

    @Autowired
    public TrainingRusToEngController(TrainingTranslateWordService trainingTranslateWordRusToEngService) {

        this.trainingTranslateWordRusToEngService = trainingTranslateWordRusToEngService;
    }

    @GetMapping
    @JsonView(View.TrainingRusToEng.class)
    private TrainingTranslateWordRusToEng get(Authentication authentication, @RequestParam boolean isLearned)
            throws ServiceException {

        long userId = (Long) authentication.getPrincipal();
        return (TrainingTranslateWordRusToEng) trainingTranslateWordRusToEngService.get(userId, isLearned);
    }

    @PostMapping
    private long answer(Authentication authentication, @RequestBody TrainingTranslateWordRusToEng rusToEng)
            throws ServiceException {

        long userId = (Long) authentication.getPrincipal();
        return trainingTranslateWordRusToEngService.checkAnswer(userId, rusToEng);
    }
}
