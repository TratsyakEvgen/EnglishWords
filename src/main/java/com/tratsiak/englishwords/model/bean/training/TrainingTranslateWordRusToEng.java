package com.tratsiak.englishwords.model.bean.training;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.util.json.View;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonView({View.TrainingRusToEng.class})
public class TrainingTranslateWordRusToEng extends TrainingTranslateWord {

    public TrainingTranslateWordRusToEng(LearningWord learningWord) {
        this.learningWordId = learningWord.getId();
        Word word = learningWord.getWord();
        this.translatedWord = word.getRussian();
        options = new ArrayList<>();
        options.add(word);
    }

    @Override
    public void incCountCorrect(LearningWord learningWord) {
        int count = learningWord.getCountCorrectRusToEng();
        learningWord.setCountCorrectRusToEng(++count);
        learningWord.setTrainingRusToEngDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void incCountIncorrect(LearningWord learningWord) {
        int count = learningWord.getCountIncorrectRusToEng();
        learningWord.setCountIncorrectRusToEng(++count);
        learningWord.setTrainingRusToEngDate(Timestamp.valueOf(LocalDateTime.now()));
    }

}
