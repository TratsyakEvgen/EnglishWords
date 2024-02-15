package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.trainig.TrainingTranslateWord;

public interface TrainingTranslateWordService {

    TrainingTranslateWord get() throws ServiceException;

    long checkAnswer(TrainingTranslateWord trainingTranslateWord) throws ServiceException;
}
