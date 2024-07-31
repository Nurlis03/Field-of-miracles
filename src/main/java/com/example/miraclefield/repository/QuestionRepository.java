package com.example.miraclefield.repository;

import com.example.miraclefield.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    boolean existsByQuestionText(String questionText);
    @Query(
            value = """
                SELECT q.*
                FROM question q
                WHERE NOT EXISTS (
                    SELECT 1
                    FROM game_history gh
                    WHERE gh.question_id = q.id
                        AND gh.user_id = :userId
                        AND gh.answer_status = 'YES'
                )
                ORDER BY RANDOM()
                LIMIT 1
            """,
            nativeQuery = true
    )
    Question findRandomUnansweredQuestionByUserId(@Param("userId") Long userId);
}
