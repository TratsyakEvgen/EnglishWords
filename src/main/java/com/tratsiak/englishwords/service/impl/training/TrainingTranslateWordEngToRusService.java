package com.tratsiak.englishwords.service.impl.training;


import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWord;
import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordEngToRus;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.repository.MistakeRepository;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.exception.ErrorMessages;
import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class TrainingTranslateWordEngToRusService extends TrainingTranslateWordBaseService {

    @Autowired
    public TrainingTranslateWordEngToRusService(LearningWordRepository learningWordRepository,
                                                MistakeRepository mistakeRepository,
                                                WordRepository wordRepository) {
        super(learningWordRepository, mistakeRepository, wordRepository);
    }

    @Override
    public TrainingTranslateWord get(long userId, boolean isLearned) throws ServiceException {

        try {
            LearningWord learningWord = learningWordRepository.findWithMinDateEngToRusFetchWord(userId, isLearned)
                    .orElseThrow(() -> new ServiceException(LevelException.INFO,
                            isLearned ? ErrorMessages.DICTIONARY_EMPTY_REPEAT : ErrorMessages.DICTIONARY_EMPTY_LEARN,
                            String.format("Learning word for training status %b, for user %d not found",
                                    isLearned, userId)
                    ));

            TrainingTranslateWordEngToRus engToRus = new TrainingTranslateWordEngToRus(learningWord);
            completeTrainingTranslateWord(engToRus, learningWord);
            return engToRus;

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR,
                    isLearned ? ErrorMessages.DICTIONARY_EMPTY_REPEAT : ErrorMessages.DICTIONARY_EMPTY_LEARN,
                    String.format("Repository exception! Status %b user %d", isLearned, userId), e);
        }
    }
}
