package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.WordService;
import com.tratsiak.englishwords.util.json.PageJsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/words")
public class WordController {

    private final WordService wordService;

    @Autowired
    public WordController(WordService wordService) {
        this.wordService = wordService;
    }
    @JsonView(View.Word.class)
    @GetMapping
    @ResponseBody
    private Page<Word> findWords(@RequestParam("part") String partWord, @Valid PageInfo pageInfo) {
        try {
            Page<Word> words = wordService.findWords(partWord, pageInfo);
            return new PageJsonView<>(words);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @JsonView(View.LearningWord.class)
    @GetMapping("/{id}")
    private Word findWord(@PathVariable("id") long id){
        try {
            return wordService.findWordById(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }
}
