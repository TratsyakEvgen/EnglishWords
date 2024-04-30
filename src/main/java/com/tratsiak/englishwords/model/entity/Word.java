package com.tratsiak.englishwords.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "words")
public class Word implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({View.Word.class,
            View.LearningWord.class,
            View.TrainingEngToRus.class,
            View.TrainingRusToEng.class,
            View.WordFetchLearningWord.class})
    @Column(name = "words_id")
    private long id;

    @JsonView({View.Word.class,
            View.LearningWord.class,
            View.TrainingRusToEng.class,
            View.WordFetchLearningWord.class})
    @Column(name = "english")
    private String english;

    @JsonView({View.Word.class,
            View.LearningWord.class,
            View.TrainingRusToEng.class,
            View.WordFetchLearningWord.class})
    @Column(name = "transcription")
    private String transcription;

    @JsonView({View.Word.class,
            View.LearningWord.class,
            View.TrainingEngToRus.class,
            View.WordFetchLearningWord.class})
    @Column(name = "russian")
    private String russian;

    @JsonView({View.LearningWord.class,
            View.TrainingRusToEng.class,
            View.WordFetchLearningWord.class})
    @Column(name = "sound")
    private boolean sound;

    @JsonView({View.WordFetchLearningWord.class})
    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
    private List<LearningWord> learningWord;

    @JsonView
    @OneToMany(mappedBy = "wrongWord", fetch = FetchType.LAZY)
    private List<Mistake> wrongWords;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Word word = (Word) o;
        return id == word.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", english='" + english + '\'' +
                ", transcription='" + transcription + '\'' +
                ", russian='" + russian + '\'' +
                ", sound=" + sound +
                '}';
    }
}
