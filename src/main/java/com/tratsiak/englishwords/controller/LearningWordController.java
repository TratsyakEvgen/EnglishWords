package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.service.LearningWordService;
import com.tratsiak.englishwords.service.ServiceException;
import com.tratsiak.englishwords.util.json.PageJsonView;
import com.tratsiak.englishwords.util.json.View;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/learningWords")
public class LearningWordController {

    private final LearningWordService learningWordService;

    @Autowired
    public LearningWordController(LearningWordService learningWordService) {
        this.learningWordService = learningWordService;
    }

    @GetMapping
    @ResponseBody
    @JsonView(View.Word.class)
    private PageJsonView<LearningWord> getAll(PageInfo pageInfo){
        try {
            Page<LearningWord> page = learningWordService.getAll(pageInfo);
            return new PageJsonView<>(page);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private LearningWord create(@RequestBody Word word) {
        try {
            return learningWordService.create(word);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    private void delete(@PathVariable("id") long id) {
        try {
            learningWordService.delete(id);
        } catch (ServiceException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

    }
}