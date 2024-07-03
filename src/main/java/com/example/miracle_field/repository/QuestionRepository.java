package com.example.miracle_field.repository;

import com.example.miracle_field.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    @Query(value = "SELECT * FROM question ORDER BY RANDOM() LIMIT 1", nativeQuery = true)
    Question findRandomQuestion();
    boolean existsByQuestionText(String questionText);
}
