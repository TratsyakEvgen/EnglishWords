package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;

public interface TrainingTranslateWordService {

    TrainingTranslateWordRusToEng get(boolean isLearned) throws ServiceException;

    long checkAnswer(TrainingTranslateWordRusToEng trainingTranslateWordRusToEng) throws ServiceException;
}
