package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.web.dto.GameAnswerDto;
import com.example.miraclefield.entity.AnswerStatus;
import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import com.example.miraclefield.service.GameHistoryService;
import com.example.miraclefield.service.QuestionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class GameService {

    private QuestionService questionService;
    private GameHistoryService gameHistoryService;
    private GameProgress gameProgress;
    private GuessLogic guessLogic;
    private PointLogic pointLogic;

    public String showGameBoard(User user, Model model) {

        Question question = questionService.findUniqueUnansweredQuestionForUser(user.getId());

        if (question == null) {
            model.addAttribute("user", user);
            return "game-over";
        }

        List<GameHistory> questionSpecificHistories = question.getGameHistories().stream()
                .filter(history -> history.getUser().getId().equals(user.getId()))
                .toList();

        String userAnswerProgress = gameProgress.initAnswerProgress(question.getAnswer(), questionSpecificHistories);

        return setupGameModel(model, question, questionSpecificHistories, user, userAnswerProgress);
    }

    public String checkAnswer(User user, @ModelAttribute("gameAnswerDto") @Valid GameAnswerDto gameAnswerDto, BindingResult result, Model model) {

        Question currentQuestion = gameAnswerDto.getQuestion();
        List<GameHistory> questionSpecificHistories = gameAnswerDto.getQuestionSpecificHistories();
        String userAnswer = gameAnswerDto.getUserAnswer().trim();
        String userAnswerProgress = gameAnswerDto.getUserAnswerProgress();

        if (result.hasErrors()) {
            model.addAttribute("gameAnswerDto", gameAnswerDto);
            return "game";
        }

        AnswerStatus answerStatus = guessLogic.guess(userAnswer, currentQuestion.getAnswer(), userAnswerProgress);
        pointLogic.givePoint(userAnswer, userAnswerProgress, user, answerStatus);

        GameHistory gameHistory = createGameHistory(user, currentQuestion, answerStatus, userAnswer);
        gameHistoryService.save(gameHistory);
        questionSpecificHistories.add(gameHistory);

        if (answerStatus == AnswerStatus.NO || answerStatus == AnswerStatus.GUESS_CHARACTER) { // continue game
            return setupGameModel(model, currentQuestion, questionSpecificHistories, user, gameProgress.getCurrentProgress());
        }
        return "success";
    }

    private String setupGameModel(Model model, Question question,
                                  List<GameHistory> questionSpecificHistories,
                                  User user, String userAnswerProgress) {

        GameAnswerDto gameAnswerDto = GameAnswerDto.builder()
                .question(question)
                .questionSpecificHistories(questionSpecificHistories)
                .user(user)
                .userAnswerProgress(userAnswerProgress)
                .build();

        model.addAttribute("gameAnswerDto", gameAnswerDto);
        return "game";
    }

    private GameHistory createGameHistory(User user, Question question,
                                          AnswerStatus answerStatus, String answer) {
        return GameHistory.builder()
                .user(user)
                .question(question)
                .answerStatus(answerStatus)
                .timeProgress(LocalDateTime.now())
                .userAnswer(answer)
                .build();
    }
}
