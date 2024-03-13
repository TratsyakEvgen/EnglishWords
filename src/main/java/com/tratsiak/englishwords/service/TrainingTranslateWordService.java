package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;

public interface TrainingTranslateWordService {

    TrainingTranslateWordRusToEng get(long userId, boolean isLearned) throws ServiceException;

    long checkAnswer(long userId, TrainingTranslateWordRusToEng trainingTranslateWordRusToEng) throws ServiceException;
}
