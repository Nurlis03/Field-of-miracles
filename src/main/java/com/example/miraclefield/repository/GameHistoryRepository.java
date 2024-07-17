package com.example.miraclefield.repository;

import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserAndQuestion(UserDetails user, Question question);
    List<GameHistory> findByUser(UserDetails user);

    List<GameHistory> findByQuestion(Question question);

    @Query("SELECT gh.question.id FROM GameHistory gh WHERE gh.user = :user")
    Set<Long> findAnsweredQuestionIdsByUser(@Param("user") UserDetails user);
}