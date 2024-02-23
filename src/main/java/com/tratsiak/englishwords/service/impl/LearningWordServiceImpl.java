package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.LearningWordRepository;
import com.tratsiak.englishwords.service.LearningWordService;
import com.tratsiak.englishwords.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    public Page<LearningWord> getAll(PageInfo pageInfo) throws ServiceException {
        try {
            Pageable pageable = pageInfo.getPageable();
            return learningWordRepository.findAllFetchAll(pageable);
        } catch (DataAccessException e) {
            throw new ServiceException("Can't get all learning words", e);
        }
    }

    @Override
    public LearningWord getByWordId(int id) throws ServiceException {
        try {
            Optional<LearningWord> optionalLearningWord =
                    learningWordRepository.findByWord(Word.builder().id(id).build());

            return optionalLearningWord.orElseThrow(() ->
                    new ServiceException(String.format("Learning word with word id %d not found", id)));
        } catch (DataAccessException e) {
            throw new ServiceException("Can't get learning words by word id", e);
        }
    }

    @Override
    public LearningWord create(Word word) throws ServiceException {
        try {

            Optional<LearningWord> optionalLearningWord = learningWordRepository.findByWord(word);

            if (optionalLearningWord.isPresent()) {
                throw new ServiceException("This word is already being studied");
            }


            LearningWord learningWord = LearningWord
                    .builder()
                    .word(word)
                    .build();
            return learningWordRepository.save(learningWord);
        } catch (DataAccessException e) {
            throw new ServiceException("Can't creat new learning word", e);
        }


    }

    @Override
    public void delete(long id) throws ServiceException {
        try {
            learningWordRepository.deleteById(id);
        } catch (DataAccessException e) {
            throw new ServiceException("Can't delete learning word");
        }
    }
}
