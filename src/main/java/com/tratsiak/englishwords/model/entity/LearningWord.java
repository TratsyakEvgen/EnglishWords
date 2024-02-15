package com.tratsiak.englishwords.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Timestamp;
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
    @JsonView({View.LearningWord.class, View.Word.class})
    private long id;

    @ManyToOne
    @JoinColumn(name = "words_id")
    @JsonView(View.Word.class)
    private Word word;

    @Column(name = "count_eng_to_rus")
    @JsonView(View.Word.class)
    private byte countCorrectEngToRus;

    @Column(name = "count_rus_to_eng")
    @JsonView(View.Word.class)
    private byte countCorrectRusToEng;

    @Column(name = "training_date")
    @JsonView(View.Word.class)
    private Timestamp trainingDate;

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
                '}';
    }
}
