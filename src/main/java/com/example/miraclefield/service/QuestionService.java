package com.example.miraclefield.service;

import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import com.example.miraclefield.repository.GameHistoryRepository;
import com.example.miraclefield.repository.QuestionRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class QuestionService {

    private QuestionRepository questionRepository;
    private GameHistoryRepository gameHistoryRepository;

    public void save(Question question) {
        questionRepository.save(question);
    }

    public List<Question> findAll() {
        return questionRepository.findAll();
    }

    public Question findById(Long questionId) {
        return questionRepository.findById(questionId).orElseThrow(() -> new RuntimeException("Question with ID " + questionId + " not found"));
    }

    public void deleteById(Long questionId) {
        questionRepository.deleteById(questionId);
    }

    public Question findUniqueUnansweredQuestionForUser(Long userId) {
        return questionRepository.findRandomUnansweredQuestionByUserId(userId);
    }

    public void deleteRelatedGameHistories(Question question) {
        List<GameHistory> gameHistories = gameHistoryRepository.findByQuestion(question);
        gameHistoryRepository.deleteAll(gameHistories);
    }

    public boolean existsByQuestionText(String questionText) {
        return questionRepository.existsByQuestionText(questionText);
    }
}
