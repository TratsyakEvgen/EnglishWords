package com.tratsiak.englishwords.service;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.service.exception.ServiceException;
import org.springframework.data.domain.Page;

public interface WordService {

    Page<Word> findWords(String partWord, PageInfo pageInfo) throws ServiceException;

    Word getByIdFetchLearningWord(long id) throws ServiceException;

}
