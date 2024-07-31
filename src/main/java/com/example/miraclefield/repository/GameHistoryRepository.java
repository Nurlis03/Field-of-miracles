package com.example.miraclefield.repository;

import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserAndQuestion(UserDetails user, Question question);

    List<GameHistory> findByQuestion(Question question);
}