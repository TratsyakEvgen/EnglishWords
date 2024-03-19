package com.tratsiak.englishwords.repository;

import com.tratsiak.englishwords.model.entity.LearningWord;
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

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word WHERE l.user.id = ?1")
    Page<LearningWord> findAllFetchAll(long userId, Pageable pageable);

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word " +
            "WHERE l.user.id = ?1 AND l.learnedStatus = ?2 ORDER BY l.trainingRusToEngDate ASC LIMIT 1")
    Optional<LearningWord> findWithMinDateRusToEngFetchWord(long userId, boolean isLearned);

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word " +
            "WHERE l.user.id = ?1 AND l.learnedStatus = ?2 ORDER BY l.trainingEngToRusDate ASC LIMIT 1")
    Optional<LearningWord> findWithMinDateEngToRusFetchWord(long userId, boolean isLearned);

    @Query("SELECT l FROM LearningWord l LEFT JOIN FETCH l.word WHERE l.user.id = ?1 ORDER BY RAND()")
    List<LearningWord> findLimitLearningWordByUserId(long userId, Limit limit);

    Optional<LearningWord> findByUserIdAndWordId(long userId, long WordId);

    Optional<LearningWord> findByUserIdAndId(long userId, long learningWordId);

    void deleteById(long id);

}
