package com.tratsiak.englishwords.service.impl.training;

import com.tratsiak.englishwords.model.bean.training.TrainingTranslateWord;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Mistake;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.repository.MistakeRepository;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.TrainingTranslateWordService;
import com.tratsiak.englishwords.service.exception.ErrorMessages;
import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Limit;

import java.util.*;

abstract class TrainingTranslateWordBaseService implements TrainingTranslateWordService {
    private final static int TOTAL = 5;

    protected final LearningWordRepository learningWordRepository;

    protected final MistakeRepository mistakeRepository;

    protected final WordRepository wordRepository;


    protected TrainingTranslateWordBaseService(LearningWordRepository learningWordRepository,
                                               MistakeRepository mistakeRepository,
                                               WordRepository wordRepository) {
        this.learningWordRepository = learningWordRepository;
        this.mistakeRepository = mistakeRepository;
        this.wordRepository = wordRepository;
    }

    @Override
    public abstract TrainingTranslateWord get(long userId, boolean isLearned) throws ServiceException;

    @Override
    public long checkAnswer(long userId, TrainingTranslateWord trainingTranslateWord) throws ServiceException {

        try {
            long learningWordId = trainingTranslateWord.getLearningWordId();

            LearningWord learningWord = learningWordRepository.findByUserIdAndId(userId, learningWordId)
                    .orElseThrow(() -> new ServiceException(LevelException.WARM, ErrorMessages.CHECK_TRAINING,
                            String.format("Learning word for training %s, for user %d not found",
                                    trainingTranslateWord, userId)
                    ));

            Word word = learningWord.getWord();
            long rightWordId = word.getId();
            long answer = trainingTranslateWord.getAnswer();

            if (answer == rightWordId) {
                trainingTranslateWord.incCountCorrect(learningWord);
            } else {

                trainingTranslateWord.incCountIncorrect(learningWord);

                Word wrongWord = wordRepository.findById(answer)
                        .orElseThrow(() -> new ServiceException(LevelException.WARM, ErrorMessages.CHECK_TRAINING,
                                String.format("Word id %d for training %s, for user %d not found",
                                        answer, trainingTranslateWord, userId)
                        ));

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
            throw new ServiceException(LevelException.ERROR, ErrorMessages.CHECK_TRAINING,
                    String.format("Repository exception! User %d, training %s", userId, trainingTranslateWord), e);
        }
    }

    protected void completeTrainingTranslateWord(TrainingTranslateWord trainingTranslateWord,
                                                 LearningWord learningWord) {

        List<Word> options = trainingTranslateWord.getOptions();
        List<Mistake> mistakes = learningWord.getMistake();
        List<Word> words = mistakes.stream().limit(TOTAL - 1).map(Mistake::getWrongWord).toList();
        options.addAll(words);

        int size = options.size();
        if (size < TOTAL) {
            Set<Word> optionsSet = new HashSet<>(options);

            List<Word> wordList = wordRepository
                    .getMistakesByLearningWord(learningWord.getWord().getEnglish(), Limit.of(TOTAL));
            fillSet(optionsSet, wordList);

            size = optionsSet.size();


            if (size < TOTAL) {
                List<LearningWord> learningWords = learningWordRepository
                        .findLimitLearningWordByUserId(learningWord.getUser().getId(), Limit.of(TOTAL));

                wordList = new ArrayList<>(learningWords.stream().map(LearningWord::getWord).toList());

                fillSet(optionsSet, wordList);
                size = optionsSet.size();
            }

            if (size < TOTAL) {
                wordList = wordRepository.findRandom(Limit.of(TOTAL));

                fillSet(optionsSet, wordList);
            }
            options = new ArrayList<>(optionsSet);
        }

        Collections.shuffle(options);
        trainingTranslateWord.setOptions(options);
    }

    private void fillSet(Set<Word> optionsSet, List<Word> wordList) {

        while (optionsSet.size() < TOTAL & !wordList.isEmpty()) {
            optionsSet.add(wordList.remove(0));
        }
    }

}
