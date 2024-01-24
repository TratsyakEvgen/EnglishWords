package com.tratsiak.englishwords.controller;

import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.service.WordService;
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

    @GetMapping
    @ResponseBody
    private Page<Word> findWord(@RequestParam("part") String partWord, @Valid PageInfo pageInfo) {
        try {
            return wordService.findWord(partWord, pageInfo);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Words not found", e);
        }
    }
}
