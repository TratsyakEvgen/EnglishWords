package com.tratsiak.englishwords.repository;

import com.tratsiak.englishwords.model.entity.Word;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


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
    Word findWordByIdFetchLerningWord(long id);
}

