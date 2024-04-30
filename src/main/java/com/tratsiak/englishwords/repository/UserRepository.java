package com.tratsiak.englishwords.repository;

import com.tratsiak.englishwords.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByTelegramId(long id);

    User findByLogin(String login);
}
