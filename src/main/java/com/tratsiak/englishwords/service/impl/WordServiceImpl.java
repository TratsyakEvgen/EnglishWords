package com.tratsiak.englishwords.service.impl;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.repository.WordRepository;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.WordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class WordServiceImpl implements WordService {

    private final WordRepository wordRepository;

    @Autowired
    public WordServiceImpl(WordRepository wordRepository) {
        this.wordRepository = wordRepository;
    }

    @Override
    public Page<Word> findWord(String partWord, PageInfo pageInfo) throws ServiceException {
        try {
            Pageable pageable = pageInfo.getPageable();
            return wordRepository.findWord(partWord, pageable);
        } catch (DataAccessException e) {
            throw new ServiceException("Can't find words of part", e);
        }
    }
}
