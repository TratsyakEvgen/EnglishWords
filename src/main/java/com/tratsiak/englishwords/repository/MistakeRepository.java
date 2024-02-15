package com.tratsiak.englishwords.repository;

import com.tratsiak.englishwords.model.entity.Mistake;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MistakeRepository extends JpaRepository<Mistake, Long> {
}
