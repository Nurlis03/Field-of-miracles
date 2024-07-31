package com.example.miraclefield.web.dto;

import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import com.example.miraclefield.entity.User;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class GameAnswerDto {

    private Question question;

    private List<GameHistory> questionSpecificHistories;

    @NotBlank(message = "Answer cannot be empty")
    private String userAnswer;

    private String userAnswerProgress;

    private User user;
}
