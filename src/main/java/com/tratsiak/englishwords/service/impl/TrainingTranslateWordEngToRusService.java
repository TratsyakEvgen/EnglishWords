package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWord;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.repository.MistakeRepository;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TrainingTranslateWordEngToRusService extends TrainingTranslateWordRusToEngService {

    @Autowired
    public TrainingTranslateWordEngToRusService(LearningWordRepository learningWordRepository,
                                                MistakeRepository mistakeRepository,
                                                WordRepository wordRepository) {
        super(learningWordRepository, mistakeRepository, wordRepository);
    }

    @Override
    public TrainingTranslateWordRusToEng get(long userId, boolean isLearned) throws ServiceException {
        try {
            LearningWord learningWord = learningWordRepository
                    .findWithMinDateEngToRusFetchWord(userId, isLearned)
                    .orElseThrow(() -> new ServiceException("Learning word not found"));
            TrainingTranslateWordRusToEng trainingTranslateWordRusToEng = new TrainingTranslateWord(learningWord);
            return completeTrainingTranslateWord(trainingTranslateWordRusToEng, learningWord);
        } catch (DataAccessException e) {
            throw new ServiceException("Can't get training translate word eng to rus", e);
        }
    }
}
