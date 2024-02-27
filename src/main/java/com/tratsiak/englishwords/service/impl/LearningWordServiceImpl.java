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
    public LearningWord create(Word word) throws ServiceException {
        try {

            Optional<LearningWord> optionalLearningWord = learningWordRepository.findLearningWordByWord(word);

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
    public LearningWord update(LearningWord learningWord) throws ServiceException {
        try {
            Optional<LearningWord> optionalLearningWord = learningWordRepository.findById(learningWord.getId());
            LearningWord learningWordFromRepository = optionalLearningWord.orElseThrow(
                    () -> new ServiceException("Learning word not found"));

            learningWordFromRepository.setLearnedStatus(learningWord.isLearnedStatus());
            learningWordRepository.save(learningWordFromRepository);

            return learningWordFromRepository;
        } catch (DataAccessException e) {
            throw new ServiceException("Can't update learning word", e);
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
