package com.tratsiak.englishwords.model.bean.training;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.util.json.View;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonView({View.TrainingRusToEng.class, View.TrainingEngToRus.class})
public class TrainingTranslateWordRusToEng implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected long learningWordId;

    protected String translatedWord;

    protected List<Word> options;

    protected long answer;

    public TrainingTranslateWordRusToEng(LearningWord learningWord) {
        this.learningWordId = learningWord.getId();
        Word word = learningWord.getWord();
        this.translatedWord = word.getRussian();
        options = new ArrayList<>();
        options.add(word);
    }


    public void incCountCorrect(LearningWord learningWord) {
        int count = learningWord.getCountCorrectRusToEng();
        learningWord.setCountCorrectRusToEng(++count);
        learningWord.setTrainingRusToEngDate(Timestamp.valueOf(LocalDateTime.now()));
    }


    public void incCountIncorrect(LearningWord learningWord) {
        int count = learningWord.getCountIncorrectRusToEng();
        learningWord.setCountIncorrectRusToEng(++count);
        learningWord.setTrainingRusToEngDate(Timestamp.valueOf(LocalDateTime.now()));
    }

}
