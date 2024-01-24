package com.tratsiak.englishwords.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @Column(name = "words_id")
    private long id;

    @Column(name = "english")
    private String english;

    @Column(name = "transcription")
    private String transcription;

    @Column(name = "russian")
    private String russian;

    @JsonIgnore
    @OneToMany(mappedBy = "word", fetch = FetchType.LAZY)
    private List<LearningWord> learningWord;

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
                '}';
    }
}
