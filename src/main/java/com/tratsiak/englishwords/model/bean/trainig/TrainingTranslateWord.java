package com.tratsiak.englishwords.model.bean.trainig;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.util.json.View;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
@ToString
@JsonView({View.TrainingRusToEng.class, View.TrainingEngToRus.class})
public class TrainingTranslateWord implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    protected long LearningWordId;

    protected String translatedWord;

    protected List<Word> options;

    protected long answer;

    public TrainingTranslateWord(LearningWord learningWord) {
        this.LearningWordId = learningWord.getId();
        Word word = learningWord.getWord();
        this.translatedWord = word.getRussian();
        options = new ArrayList<>();
        options.add(word);
    }

    public void setCountIfTrue(LearningWord learningWord){
        byte count = learningWord.getCountCorrectRusToEng();
        if (count < 3) {
            learningWord.setCountCorrectRusToEng(++count);
        }
    }

    public void setCountIfFalse(LearningWord learningWord){
        learningWord.setCountCorrectRusToEng((byte) 0);
    }
}
