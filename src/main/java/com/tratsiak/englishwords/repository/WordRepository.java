package com.tratsiak.englishwords.repository;

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
public interface WordRepository extends JpaRepository<Word, Long> {
    @Query("SELECT w FROM Word w WHERE w.english ILIKE CONCAT('%',?1,'%') OR w.russian ILIKE CONCAT('%',?1,'%') " +
            "ORDER BY CASE " +
            "WHEN w.english ILIKE CONCAT('',?1,'') OR w.russian ILIKE CONCAT('',?1,'')THEN 1 " +
            "WHEN w.english ILIKE CONCAT('',?1,'%') OR w.russian ILIKE CONCAT('',?1,'%') THEN 2 " +
            "WHEN w.english ILIKE CONCAT('%',?1,'') OR w.russian ILIKE CONCAT('%',?1,'') THEN 4 " +
            "ELSE 3 " +
            "END"
    )
    Page<Word> findWord(String partWord, Pageable pageable);

    @Query("SELECT w FROM Word w LEFT JOIN FETCH w.learningWord WHERE w.id = ?1")
    Optional<Word> findWordByIdFetchLearningWord(long id);

    @Query(value = "select ANY_VALUE(m.id), m.wrong_word, ANY_VALUE(m.learning_word), w.* from mistakes m " +
            "left join words w ON m.wrong_word = w.words_id " +
            "left join learning_word l on m.learning_word = l.learning_word_id " +
            "WHERE w.english = ?1 group by 2", nativeQuery = true)
    List<Word> getMistakesByLearningWord(String word, Limit limit);


    @Query(value = "SELECT w FROM Word w ORDER BY RAND()")
    List<Word> findRandom(Limit limit);

}

