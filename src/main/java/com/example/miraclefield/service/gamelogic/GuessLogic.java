package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.AnswerStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GuessLogic {

    private GuessCharacterImpl guessCharacter;
    private GuessWordImpl guessWord;
    public AnswerStatus guess(String userAnswer, String questionAnswer, String userAnswerProgress) {
        Guess guess;
        if (userAnswer.length() == 1) {
            guess = guessCharacter;
        }
        else {
            guess = guessWord;
        }
        return guess.guess(userAnswer, questionAnswer, userAnswerProgress);
    }
}
