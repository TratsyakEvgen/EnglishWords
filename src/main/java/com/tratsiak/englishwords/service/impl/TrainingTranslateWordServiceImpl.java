package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.trainig.TrainingTranslateWord;
import com.tratsiak.englishwords.model.bean.trainig.TrainingTranslateWordEngToRus;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Mistake;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.repository.MistakeRepository;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Limit;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Service
public class TrainingTranslateWordServiceImpl implements TrainingTranslateWordService {

    protected final LearningWordRepository learningWordRepository;

    protected final MistakeRepository mistakeRepository;

    protected final WordRepository wordRepository;

    @Autowired
    public TrainingTranslateWordServiceImpl(LearningWordRepository learningWordRepository, MistakeRepository mistakeRepository, WordRepository wordRepository) {
        this.learningWordRepository = learningWordRepository;
        this.mistakeRepository = mistakeRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public TrainingTranslateWord get() throws ServiceException {
        try {

            LearningWord learningWord = learningWordRepository
                    .findWithMinDateAndCountCorrectRusToEngFetchWord()
                    .orElseThrow(() -> new ServiceException("Learning word not found"));
            TrainingTranslateWord trainingTranslateWord = new TrainingTranslateWord(learningWord);

            return completeTrainingTranslateWord(trainingTranslateWord, learningWord);

        } catch (DataAccessException e) {
            throw new ServiceException("Can't get training translate word rus to eng");
        }
    }

    @Override
    public long checkAnswer(TrainingTranslateWord trainingTranslateWord) throws ServiceException {
        try {
            LearningWord learningWord = learningWordRepository
                    .findById(trainingTranslateWord.getLearningWordId())
                    .orElseThrow(() -> new ServiceException("Learning word not found"));

            learningWord.setTrainingDate(Timestamp.valueOf(LocalDateTime.now()));

            Word word = learningWord.getWord();
            long rightWordId = word.getId();
            long answer = trainingTranslateWord.getAnswer();

            if (answer == rightWordId) {
                trainingTranslateWord.setCountIfTrue(learningWord);

            } else {

                trainingTranslateWord.setCountIfFalse(learningWord);

                Word wrongWord = wordRepository.findById(answer)
                        .orElseThrow(() -> new ServiceException("Word not found"));

                Mistake mistake = Mistake
                        .builder()
                        .misspelledWord(word)
                        .wrongWord(wrongWord)
                        .build();

                mistakeRepository.save(mistake);
            }

            learningWordRepository.save(learningWord);

            return rightWordId;
        } catch (DataAccessException e) {
            throw new ServiceException("Can't check answer training translate word rus to eng");
        }
    }

    protected TrainingTranslateWord completeTrainingTranslateWord(TrainingTranslateWord trainingTranslateWord,
                                                                  LearningWord learningWord) {
        List<LearningWord> learningWords =
                learningWordRepository.findLimitExcludingLearningWord(learningWord.getId(), Limit.of(4));
        List<Word> words = learningWords.stream().map(LearningWord::getWord).toList();

        List<Word> options = trainingTranslateWord.getOptions();
        options.addAll(words);
        Collections.shuffle(options);

        return trainingTranslateWord;
    }
}
