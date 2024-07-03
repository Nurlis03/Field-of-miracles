package com.example.miracle_field.service;

import com.example.miracle_field.entity.GameHistory;
import com.example.miracle_field.entity.Question;
import com.example.miracle_field.entity.User;
import com.example.miracle_field.repository.GameHistoryRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GameHistoryService {
    private GameHistoryRepository gameHistoryRepository;

    public void save(GameHistory gameHistory) {
        gameHistoryRepository.save(gameHistory);
    }

    public List<GameHistory> findByUserAndQuestion(User user, Question question) {
        return gameHistoryRepository.findByUserAndQuestion(user, question);
    }
}