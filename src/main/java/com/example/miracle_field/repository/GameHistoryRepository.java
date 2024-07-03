package com.example.miracle_field.repository;

import com.example.miracle_field.entity.GameHistory;
import com.example.miracle_field.entity.Question;
import com.example.miracle_field.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface GameHistoryRepository extends JpaRepository<GameHistory, Long> {
    List<GameHistory> findByUserAndQuestion(User user, Question question);
    List<GameHistory> findByUser(User user);

    List<GameHistory> findByQuestion(Question question);

    @Query("SELECT gh.question.id FROM GameHistory gh WHERE gh.user = :user")
    Set<Long> findAnsweredQuestionIdsByUser(@Param("user") User user);
}