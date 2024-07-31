package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.GameHistory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Getter
@Setter
public class GameProgress {

    private String currentProgress;
    public String initAnswerProgress(String questionAnswer, List<GameHistory> gameHistories) {
        char[] progressChars = questionAnswer.replaceAll("[^ ]", "*").toCharArray();

        for (GameHistory gh : gameHistories) {
            String userAnswer = gh.getUserAnswer().toLowerCase();
            for (int i = 0; i < questionAnswer.length(); i++) {
                if (userAnswer.indexOf(Character.toLowerCase(questionAnswer.charAt(i))) >= 0) {
                    progressChars[i] = questionAnswer.charAt(i);
                }
            }
        }
        return new String(progressChars);
    }

    public Boolean updateCurrentProgress(String currentUserAnswerProgress, String questionAnswer, String userAnswer) {
        if (!isQuestionAnswerContainUserAnswer(questionAnswer, userAnswer)) {
            return false;
        }

        StringBuilder updatedProgress = new StringBuilder(currentUserAnswerProgress);

        char guessedChar = Character.toLowerCase(userAnswer.charAt(0));
        for (int i = 0; i < questionAnswer.length(); i++) {
            if (Character.toLowerCase(questionAnswer.charAt(i)) == guessedChar) {
                updatedProgress.setCharAt(i, questionAnswer.charAt(i));
            }
        }
        currentProgress = updatedProgress.toString();
        return true;
    }

    public Boolean isQuestionAnswerContainUserAnswer(String questionAnswer, String userAnswer) {
        return questionAnswer.toLowerCase().contains(userAnswer.toLowerCase());
    }
}
