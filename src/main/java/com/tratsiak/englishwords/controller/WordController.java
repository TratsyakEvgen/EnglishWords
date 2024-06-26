package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.service.WordService;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.util.json.PageJsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {

        this.wordService = wordService;
    }

    @GetMapping
    @ResponseBody
    @JsonView(View.Word.class)
    private Page<Word> findWords(@RequestParam("part") String partWord, @Valid PageInfo pageInfo)
            throws ServiceException {

        Page<Word> words = wordService.findWords(partWord, pageInfo);
        return new PageJsonView<>(words);
    }


    @GetMapping("/{id}")
    @JsonView({View.WordFetchLearningWord.class})
    private Word getByWordId(@PathVariable("id") long id) throws ServiceException {

        return wordService.getByIdFetchLearningWord(id);
    }
}
