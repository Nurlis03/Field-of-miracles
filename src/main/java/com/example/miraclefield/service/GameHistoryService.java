package com.example.miraclefield.service;

import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.GameHistoryRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
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