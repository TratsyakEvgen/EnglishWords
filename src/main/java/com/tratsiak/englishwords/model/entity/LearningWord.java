package com.tratsiak.englishwords.model.entity;

import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "words_id")
    @JsonView(View.Word.class)
    private Word word;

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
