package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWord;
import com.tratsiak.englishwords.service.exception.ServiceException;

public interface TrainingTranslateWordService {

    TrainingTranslateWord get(long userId, boolean isLearned) throws ServiceException;

    long checkAnswer(long userId, TrainingTranslateWord trainingTranslateWord) throws ServiceException;
}
