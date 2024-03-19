package com.tratsiak.englishwords.model.bean.training;

import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public abstract class TrainingTranslateWord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    protected long learningWordId;
    protected String translatedWord;
    protected List<Word> options;
    protected long answer;

    public abstract void incCountCorrect(LearningWord learningWord);

    public abstract void incCountIncorrect(LearningWord learningWord);
}
