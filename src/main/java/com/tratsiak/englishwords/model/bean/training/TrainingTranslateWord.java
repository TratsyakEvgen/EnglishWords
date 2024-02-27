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

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@JsonView(View.TrainingEngToRus.class)
public class TrainingTranslateWord extends TrainingTranslateWordRusToEng implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String transcription;

    private boolean sound;

    public TrainingTranslateWord(LearningWord learningWord) {
        this.learningWordId = learningWord.getId();
        Word word = learningWord.getWord();
        this.translatedWord = word.getEnglish();
        this.transcription = word.getTranscription();
        this.sound = word.isSound();
        options = new ArrayList<>();
        options.add(word);
    }

    @Override
    public void incCountCorrect(LearningWord learningWord) {
        int count = learningWord.getCountCorrectEngToRus();
        learningWord.setCountCorrectEngToRus(++count);
        learningWord.setTrainingEngToRusDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public void incCountIncorrect(LearningWord learningWord) {
        int count = learningWord.getCountIncorrectEngToRus();
        learningWord.setCountIncorrectEngToRus(++count);
        learningWord.setTrainingEngToRusDate(Timestamp.valueOf(LocalDateTime.now()));
    }
}
