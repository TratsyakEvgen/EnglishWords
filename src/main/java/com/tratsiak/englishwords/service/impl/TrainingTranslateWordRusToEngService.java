package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWordRusToEng;
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

import java.util.*;

@Service
public class TrainingTranslateWordRusToEngService implements TrainingTranslateWordService {

    protected final LearningWordRepository learningWordRepository;

    protected final MistakeRepository mistakeRepository;

    protected final WordRepository wordRepository;

    @Autowired
    public TrainingTranslateWordRusToEngService(LearningWordRepository learningWordRepository,
                                                MistakeRepository mistakeRepository,
                                                WordRepository wordRepository) {
        this.learningWordRepository = learningWordRepository;
        this.mistakeRepository = mistakeRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public TrainingTranslateWordRusToEng get(boolean isLearned) throws ServiceException {
        try {

            LearningWord learningWord = learningWordRepository
                    .findWithMinDateRusToEngFetchWord(isLearned)
                    .orElseThrow(() -> new ServiceException("Learning word not found"));

            TrainingTranslateWordRusToEng trainingTranslateWordRusToEng =
                    new TrainingTranslateWordRusToEng(learningWord);

            return completeTrainingTranslateWord(trainingTranslateWordRusToEng, learningWord);

        } catch (DataAccessException e) {
            throw new ServiceException("Can't get training translate word rus to eng", e);
        }
    }

    @Override
    public long checkAnswer(TrainingTranslateWordRusToEng trainingTranslateWordRusToEng) throws ServiceException {
        try {
            LearningWord learningWord = learningWordRepository
                    .findById(trainingTranslateWordRusToEng.getLearningWordId())
                    .orElseThrow(() -> new ServiceException("Learning word not found"));

            Word word = learningWord.getWord();
            long rightWordId = word.getId();
            long answer = trainingTranslateWordRusToEng.getAnswer();

            if (answer == rightWordId) {
                trainingTranslateWordRusToEng.incCountCorrect(learningWord);

            } else {

                trainingTranslateWordRusToEng.incCountIncorrect(learningWord);

                Word wrongWord = wordRepository.findById(answer)
                        .orElseThrow(() -> new ServiceException("Word not found"));

                Optional<Mistake> optionalMistake =
                        mistakeRepository.findByLearningWordAndWrongWord(learningWord, wrongWord);

                if (optionalMistake.isEmpty()) {
                    Mistake mistake = Mistake
                            .builder()
                            .learningWord(learningWord)
                            .wrongWord(wrongWord)
                            .build();

                    mistakeRepository.save(mistake);
                }

            }

            learningWordRepository.save(learningWord);

            return rightWordId;
        } catch (DataAccessException e) {
            throw new ServiceException("Can't check answer training translate word rus to eng", e);
        }
    }

    protected TrainingTranslateWordRusToEng completeTrainingTranslateWord(
            TrainingTranslateWordRusToEng trainingTranslateWordRusToEng,
            LearningWord learningWord) {

        List<Word> options = trainingTranslateWordRusToEng.getOptions();
        List<Mistake> mistakes = learningWord.getMistake();
        List<Word> words = mistakes.stream().limit(4).map(Mistake::getWrongWord).toList();
        options.addAll(words);


        if (options.size() < 5) {
            Set<Word> optionsSet = new HashSet<>(options);

            List<LearningWord> learningWords = learningWordRepository.findLimitLearningWord(Limit.of(5));
            List<Word> wordList = new ArrayList<>(learningWords.stream().map(LearningWord::getWord).toList());

            while (optionsSet.size() < 5 & !wordList.isEmpty()) {
                optionsSet.add(wordList.remove(0));
            }

            options = new ArrayList<>(optionsSet);

        }

        Collections.shuffle(options);
        trainingTranslateWordRusToEng.setOptions(options);

        return trainingTranslateWordRusToEng;
    }
}
