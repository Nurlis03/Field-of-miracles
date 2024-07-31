package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.AnswerStatus;
import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.PointRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class PointLogic {

    private final PointRepository pointRepository;
    public void givePoint(String userAnswer, String userAnswerProgress, User user, AnswerStatus answerStatus) {
        long currentAmount = user.getPoint().getAmount();

        if (answerStatus == AnswerStatus.GUESS_CHARACTER) {
            if (userAnswerProgress.toLowerCase().contains(userAnswer.toLowerCase())) { // if user already guessed char
                return;
            }
            user.getPoint().setAmount(currentAmount + 1L);
        }
        else if (answerStatus == AnswerStatus.YES) {
            user.getPoint().setAmount(currentAmount + 5L);
        }
        else if (currentAmount == 1) {
            return;
        }
        else {
            user.getPoint().setAmount(currentAmount -1L);
        }
        pointRepository.save(user.getPoint());
    }
}
