package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.AnswerStatus;
import org.springframework.stereotype.Component;

@Component
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
