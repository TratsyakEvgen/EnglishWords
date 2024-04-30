package com.tratsiak.englishwords.controller;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.bean.PageInfo;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.service.LearningWordService;
import com.tratsiak.englishwords.service.exception.ServiceException;
import com.tratsiak.englishwords.util.json.PageJsonView;
import com.tratsiak.englishwords.util.json.View;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/learningWords")
public class LearningWordController {

    private final LearningWordService learningWordService;

    @Autowired
    public LearningWordController(LearningWordService learningWordService) {

        this.learningWordService = learningWordService;
    }

    @SecurityRequirement(name = "Bearer Authentication")
    @Operation(description = "Предоставляет слова находящееся на изучении")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Возвращает Page вне зависимости от наличия контента"),
            @ApiResponse(responseCode = "500",
                    description = "В случае непредвиденных ошибок при работе с БД",
                    content = @Content(examples = @ExampleObject(value = "{\n" +
                            "  \"timestamp\": \"2024-04-26T11:07:14.311+00:00\",\n" +
                            "  \"status\": 500,\n" +
                            "  \"error\": \"Internal Server Error\",\n" +
                            "  \"message\": \"Learning word not found\",\n" +
                            "  \"path\": \"/EnglishWords/learningWords\"\n" +
                            "}")))})
    @GetMapping(produces = "application/json")
    @ResponseBody
    @JsonView(View.LearningWord.class)
    private PageJsonView<LearningWord> getAll(Authentication authentication, @Valid PageInfo pageInfo)
            throws ServiceException {

        Page<LearningWord> page = learningWordService.getAll((Long) authentication.getPrincipal(), pageInfo);
        return new PageJsonView<>(page);
    }

    @PostMapping
    @JsonView(View.LearningWord.class)
    private LearningWord create(Authentication authentication, @RequestBody Word word) throws ServiceException {

        return learningWordService.create((Long) authentication.getPrincipal(), word);
    }

    @PutMapping
    @JsonView(View.LearningWord.class)
    private LearningWord update(Authentication authentication, @RequestBody LearningWord learningWord)
            throws ServiceException {

        return learningWordService.update((Long) authentication.getPrincipal(), learningWord);
    }

    @DeleteMapping("/{id}")
    private void delete(Authentication authentication, @PathVariable("id") long id) throws ServiceException {

        learningWordService.delete((Long) authentication.getPrincipal(), id);
    }
}
