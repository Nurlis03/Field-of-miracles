package com.example.miracle_field.service.game_logic;

import com.example.miracle_field.entity.AnswerStatus;
public class GuessCharacterImpl implements Guess {
    @Override
    public AnswerStatus guess(String userAnswer, String questionAnswer, String userAnswerProgress) {
        boolean anyMatch = questionAnswer.toLowerCase().chars().anyMatch(c -> userAnswer.toLowerCase().charAt(0) == c);

        if (questionAnswer.equals(userAnswerProgress)) {
            return AnswerStatus.YES;
        }
        else if (anyMatch) {
            return AnswerStatus.GUESS_CHARACTER;
        }
        else {
            return AnswerStatus.NO;
        }
    }
}
