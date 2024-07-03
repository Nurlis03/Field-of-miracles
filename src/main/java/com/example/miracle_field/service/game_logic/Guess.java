package com.example.miracle_field.service.game_logic;

import com.example.miracle_field.entity.AnswerStatus;

public interface Guess {
    public AnswerStatus guess(String userAnswer, String questionAnswer, String userAnswerProgress);
}
