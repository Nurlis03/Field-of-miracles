package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.AnswerStatus;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class GuessCharacterImpl implements Guess {

    private GameProgress gameProgress;

    @Override
    public AnswerStatus guess(String userAnswer, String questionAnswer, String userAnswerProgress) {
        Boolean anyMatch = gameProgress.updateCurrentProgress(userAnswerProgress, questionAnswer, userAnswer);

        if (questionAnswer.equals(gameProgress.getCurrentProgress())) {
            return AnswerStatus.YES;
        }
        else if (anyMatch) {
            return AnswerStatus.GUESS_CHARACTER;
        }
        return AnswerStatus.NO;
    }
}
