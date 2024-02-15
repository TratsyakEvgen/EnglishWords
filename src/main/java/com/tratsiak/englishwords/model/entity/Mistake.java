package com.tratsiak.englishwords.model.entity;


import com.fasterxml.jackson.annotation.JsonView;
import com.tratsiak.englishwords.util.json.View;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serial;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "mistakes")
public class Mistake {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;


    @ManyToOne
    @JoinColumn(name = "misspalled_word")
    private Word misspelledWord;

    @ManyToOne
    @JoinColumn(name = "wrong_word")
    private Word wrongWord;
}
