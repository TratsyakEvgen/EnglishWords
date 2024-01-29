package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import org.springframework.data.domain.Page;

public interface LearningWordService {

    Page<LearningWord> getAll(PageInfo pageInfo) throws ServiceException;

    LearningWord create(Word word) throws ServiceException;

    void delete(long id) throws ServiceException;
}
