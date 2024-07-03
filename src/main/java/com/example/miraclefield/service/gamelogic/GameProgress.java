package com.example.miraclefield.service.gamelogic;

import org.springframework.stereotype.Component;

@Component
public class GameProgress {
    public String initAnswerProgress(String questionAnswer) {
        return questionAnswer.replaceAll("[^ ]", "*");
    }

    public String updateCurrentProgress(String userAnswerProgress, String questionAnswer, String userAnswer) {
        if (userAnswer.length() > 1) {
            return userAnswerProgress;
        }
        StringBuilder updatedProgress = new StringBuilder(userAnswerProgress);

        char guessedChar = Character.toLowerCase(userAnswer.charAt(0));
        for (int i = 0; i < questionAnswer.length(); i++) {
            if (Character.toLowerCase(questionAnswer.charAt(i)) == guessedChar) {
                updatedProgress.setCharAt(i, questionAnswer.charAt(i));
            }
        }

        return updatedProgress.toString();
    }

}
