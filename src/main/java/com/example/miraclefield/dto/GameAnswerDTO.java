package com.example.miraclefield.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class GameAnswerDTO {

    @NotNull
    private Long questionId;

    @NotBlank(message = "Answer cannot be empty")
    private String userAnswer;

    private String userAnswerProgress;
}
