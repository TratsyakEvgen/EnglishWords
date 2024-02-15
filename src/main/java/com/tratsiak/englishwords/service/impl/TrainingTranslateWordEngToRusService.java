package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.trainig.TrainingTranslateWord;
import com.tratsiak.englishwords.model.bean.trainig.TrainingTranslateWordEngToRus;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.repository.MistakeRepository;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TrainingTranslateWordEngToRusService extends TrainingTranslateWordServiceImpl {

    @Autowired
    public TrainingTranslateWordEngToRusService(LearningWordRepository learningWordRepository,
                                                MistakeRepository mistakeRepository,
                                                WordRepository wordRepository) {
        super(learningWordRepository, mistakeRepository, wordRepository);
    }

    @Override
    public TrainingTranslateWord get() throws ServiceException {
        try {
            LearningWord learningWord = learningWordRepository
                    .findWithMinDateAndCountCorrectEngToRusFetchWord()
                    .orElseThrow(() -> new ServiceException("Learning word not found"));
            TrainingTranslateWord trainingTranslateWord = new TrainingTranslateWordEngToRus(learningWord);
            return completeTrainingTranslateWord(trainingTranslateWord, learningWord);
        } catch (DataAccessException e) {
            throw new ServiceException("Can't get training translate word eng to rus");
        }
    }
}
