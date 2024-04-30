package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.User;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.service.LearningWordService;
import com.tratsiak.englishwords.service.exception.ErrorMessages;
import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LearningWordServiceImpl implements LearningWordService {

    private final LearningWordRepository learningWordRepository;

    @Autowired
    public LearningWordServiceImpl(LearningWordRepository learningWordRepository) {
        this.learningWordRepository = learningWordRepository;
    }


    @Override
    public Page<LearningWord> getAll(long userId, PageInfo pageInfo) throws ServiceException {

        try {
            return learningWordRepository.findAllFetchAll(userId, pageInfo.getPageable());

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.LEARNING_WORD_NOT_FOUND,
                    String.format("Repository exception! Part user %d, page info %s", userId, pageInfo), e);
        }
    }


    @Override
    public LearningWord create(long userId, Word word) throws ServiceException {

        try {
            Optional<LearningWord> optional = learningWordRepository.findByUserIdAndWordId(userId, word.getId());

            if (optional.isPresent()) {
                throw new ServiceException(LevelException.INFO, ErrorMessages.WORD_STUDIED,
                        String.format("Can't create new learning word, because learning word %s exists, user %d",
                                word, userId)
                );
            }

            LearningWord learningWord = LearningWord
                    .builder()
                    .word(word)
                    .user(User.builder().id(userId).build())
                    .build();

            return learningWordRepository.save(learningWord);

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.CREATE_LEARNING_WORD,
                    String.format("Repository exception! Part user %d, word %s", userId, word), e);
        }


    }

    @Override
    public LearningWord update(long userId, LearningWord learningWord) throws ServiceException {

        try {
            LearningWord learningWordFromRepository = learningWordRepository
                    .findByUserIdAndId(userId, learningWord.getId())
                    .orElseThrow(() -> new ServiceException(LevelException.INFO, ErrorMessages.LEARNING_WORD_NOT_FOUND,
                            String.format("Can't update learning word, because learning word %s not found, user %d",
                                    learningWord, userId)
                    ));

            learningWordFromRepository.setLearnedStatus(learningWord.isLearnedStatus());
            learningWordRepository.save(learningWordFromRepository);

            return learningWordFromRepository;

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.UPDATE_LEARNING_WORD,
                    String.format("Repository exception! User %d, learning word %s", userId, learningWord), e);
        }
    }

    @Override
    public void delete(long userId, long id) throws ServiceException {

        try {
            if (learningWordRepository.findByUserIdAndId(userId, id).isPresent()) {
                learningWordRepository.deleteById(id);
            }

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.DELETE_LEARNING_WORD,
                    String.format("Repository exception! User %d, learning word %d", userId, id), e);
        }
    }


}
