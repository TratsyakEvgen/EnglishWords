package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import com.tratsiak.englishwords.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/trainingsRusToEng")
public class TrainingRusToEngController {

    private final TrainingTranslateWordService trainingTranslateWordService;

    @Autowired
    public TrainingRusToEngController(
            @Qualifier("trainingTranslateWordRusToEngService") TrainingTranslateWordService trainingTranslateWordService) {
        this.trainingTranslateWordService = trainingTranslateWordService;
    }

    @GetMapping
    @JsonView(View.TrainingRusToEng.class)
    private TrainingTranslateWordRusToEng get(@RequestParam boolean isLearned) {
        try {
            return trainingTranslateWordService.get(isLearned);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    private long answer(@RequestBody TrainingTranslateWordRusToEng trainingTranslateWordRusToEng) {
        try {
            return trainingTranslateWordService.checkAnswer(trainingTranslateWordRusToEng);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}