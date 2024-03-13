package com.tratsiak.englishwords.repository;

import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Mistake;
import com.tratsiak.englishwords.model.entity.Word;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MistakeRepository extends JpaRepository<Mistake, Long> {

    Optional<Mistake> findByLearningWordAndWrongWord(LearningWord learningWord, Word wrongWord);
    // @Query(value = "select  m.id, m.learningWord, m.wrongWord from Mistake m left join fetch m.wrongWord WHERE m.wrongWord.id = ?1")

}
