package com.tratsiak.englishwords.model.bean.trainig;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import com.tratsiak.englishwords.util.json.View;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonView(View.TrainingEngToRus.class)
public class TrainingTranslateWordEngToRus extends TrainingTranslateWord implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String transcription;

    public TrainingTranslateWordEngToRus(LearningWord learningWord) {
        this.LearningWordId = learningWord.getId();
        Word word = learningWord.getWord();
        this.translatedWord = word.getEnglish();
        this.transcription = learningWord.getWord().getTranscription();
        options = new ArrayList<>();
        options.add(word);
    }

    @Override
    public void setCountIfTrue(LearningWord learningWord) {
        byte count = learningWord.getCountCorrectEngToRus();
        if (count < 3) {
            learningWord.setCountCorrectEngToRus(++count);
        }
    }

    @Override
    public void setCountIfFalse(LearningWord learningWord) {
        learningWord.setCountCorrectEngToRus((byte) 0);
    }


}
