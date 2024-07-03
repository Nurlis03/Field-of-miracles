package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.AnswerStatus;

public interface Guess {
    public AnswerStatus guess(String userAnswer, String questionAnswer, String userAnswerProgress);
}
