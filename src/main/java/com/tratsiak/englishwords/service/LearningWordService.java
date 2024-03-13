package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.LearningWord;
import org.springframework.data.domain.Page;

public interface LearningWordService {

    Page<LearningWord> getAll(long userId, PageInfo pageInfo) throws ServiceException;

    LearningWord create(long userId, long wordId) throws ServiceException;

    LearningWord update(long userId, LearningWord learningWord) throws ServiceException;

    void delete(long userId, long id) throws ServiceException;
}
