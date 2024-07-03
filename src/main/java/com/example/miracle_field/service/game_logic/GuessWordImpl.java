package com.example.miracle_field.service.game_logic;

import com.example.miracle_field.entity.AnswerStatus;

public class GuessWordImpl implements Guess {

    @Override
    public AnswerStatus guess(String userAnswer, String questionAnswer, String userAnswerProgress) {
        if (userAnswer.equalsIgnoreCase(questionAnswer)) {
            return AnswerStatus.YES;
        }
        else {
            return AnswerStatus.NO;
        }
    }
}
