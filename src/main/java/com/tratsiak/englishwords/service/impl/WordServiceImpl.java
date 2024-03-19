package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.exception.ErrorMessages;
import com.tratsiak.englishwords.service.exception.LevelException;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Page<Word> findWords(String partWord, PageInfo pageInfo) throws ServiceException {

        try {
            return wordRepository.findWord(partWord, pageInfo.getPageable());

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.WORD_NOT_FOUND,
                    String.format("Repository exception! Part word %s, page info %s", partWord, pageInfo), e);
        }
    }

    @Override
    public Word getByIdFetchLearningWord(long id) throws ServiceException {

        try {
            return wordRepository.findWordByIdFetchLearningWord(id)
                    .orElseThrow(() -> new ServiceException(LevelException.WARM, ErrorMessages.WORD_NOT_FOUND,
                            String.format("Word with id %d not found", id)));

        } catch (DataAccessException e) {
            throw new ServiceException(LevelException.ERROR, ErrorMessages.WORD_NOT_FOUND,
                    String.format("Repository exception! Word id %d", id), e);
        }
    }

}
