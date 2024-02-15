package com.tratsiak.englishwords.repository;

import com.tratsiak.englishwords.model.entity.LearningWord;
import com.tratsiak.englishwords.model.entity.Word;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LearningWordRepository extends JpaRepository<LearningWord, Long> {

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word")
    Page<LearningWord> findAllFetchAll (Pageable pageable);
    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word ORDER BY l.countCorrectRusToEng ASC, RAND() LIMIT 1")
    Optional<LearningWord> findWithMinDateAndCountCorrectRusToEngFetchWord();

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word ORDER BY l.countCorrectEngToRus ASC, RAND() LIMIT 1")
    Optional<LearningWord> findWithMinDateAndCountCorrectEngToRusFetchWord();

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word WHERE l.id != ?1 ORDER BY RAND()")
    List<LearningWord> findLimitExcludingLearningWord(long id, Limit limit);

    Optional<LearningWord> findLearningWordByWord (Word word);
}
