package com.example.miraclefield.service;

import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import org.springframework.security.core.userdetails.UserDetails;
import com.example.miraclefield.repository.GameHistoryRepository;
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

    public List<GameHistory> findByUserAndQuestion(UserDetails user, Question question) {
        return gameHistoryRepository.findByUserAndQuestion(user, question);
    }
}