package com.tratsiak.englishwords.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "learning_word")
public class LearningWord implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "learning_word_id")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private long id;

    @ManyToOne
    @JoinColumn(name = "words_id")
    @JsonView({View.LearningWord.class})
    private Word word;

    @Column(name = "count_correct_eng_to_rus")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private int countCorrectEngToRus;

    @Column(name = "count_incorrect_eng_to_rus")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private int countIncorrectEngToRus;

    @Column(name = "training_eng_to_rus_date")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private Timestamp trainingEngToRusDate;

    @Column(name = "count_correct_rus_to_eng")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private int countCorrectRusToEng;

    @Column(name = "count_incorrect_rus_to_eng")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private int countIncorrectRusToEng;

    @Column(name = "training_rus_to_eng_date")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private Timestamp trainingRusToEngDate;

    @Column(name = "status")
    @JsonView({View.LearningWord.class,
            View.WordFetchLearningWord.class})
    private boolean learnedStatus;

    @OneToMany(mappedBy = "learningWord", fetch = FetchType.LAZY)
    private List<Mistake> mistake;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LearningWord that = (LearningWord) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "LearningWord{" +
                "id=" + id +
                ", countCorrectEngToRus=" + countCorrectEngToRus +
                ", countIncorrectEngToRus=" + countIncorrectEngToRus +
                ", trainingEngToRusDate=" + trainingEngToRusDate +
                ", countCorrectRusToEng=" + countCorrectRusToEng +
                ", countIncorrectRusToEng=" + countIncorrectRusToEng +
                ", trainingRusToEngDate=" + trainingRusToEngDate +
                ", learnedStatus=" + learnedStatus +
                '}';
    }
}
