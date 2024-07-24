package com.example.miraclefield.service.gamelogic;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.web.dto.GameAnswerDto;
import com.example.miraclefield.entity.AnswerStatus;
import com.example.miraclefield.entity.GameHistory;
import com.example.miraclefield.entity.Question;
import com.example.miraclefield.service.GameHistoryService;
import com.example.miraclefield.service.QuestionService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class GameService {

    private final QuestionService questionService;
    private final GameHistoryService gameHistoryService;
    private final GameProgress gameProgress;

    @Autowired
    public GameService(QuestionService questionService, GameHistoryService gameHistoryService, GameProgress gameProgress) {
        this.questionService = questionService;
        this.gameHistoryService = gameHistoryService;
        this.gameProgress = gameProgress;
    }

    public String showGameBoard(Model model) {
        UserDetails currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Question question = questionService.findUniqueUnansweredQuestionForUser(currentUser);
        if (question == null) {
            model.addAttribute("user", currentUser);
            return "game-over";
        }

        // Fetch game histories for the current question and user
        List<GameHistory> questionSpecificHistories = gameHistoryService.findByUserAndQuestion(currentUser, question);
        String userAnswerProgress = gameProgress.initAnswerProgress(question.getAnswer());

        model.addAttribute("question", question);
        model.addAttribute("questionSpecificHistories", questionSpecificHistories);
        model.addAttribute("user", currentUser);
        model.addAttribute("gameAnswerDTO", new GameAnswerDto());
        model.addAttribute("userAnswerProgress", userAnswerProgress);

        return "game";
    }

    public String checkAnswer(@ModelAttribute("gameAnswerDTO") @Valid GameAnswerDto gameAnswerDTO,
                              BindingResult result, Model model) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Question currentQuestion = questionService.findById(gameAnswerDTO.getQuestionId());

        List<GameHistory> questionSpecificHistories = gameHistoryService.findByUserAndQuestion(currentUser, currentQuestion);
        String userAnswer = gameAnswerDTO.getUserAnswer();
        String userAnswerProgress = gameAnswerDTO.getUserAnswerProgress();
        log.info(gameAnswerDTO.toString());

        if (result.hasErrors()) {
            return setupGameModel(model, currentQuestion, questionSpecificHistories, currentUser, userAnswerProgress);
        }

        Guess guess;
        if (userAnswer.length() == 1) {
            guess = new GuessCharacterImpl();
        } else {
            guess = new GuessWordImpl();
        }

        AnswerStatus answerStatus = guess.guess(userAnswer, currentQuestion.getAnswer(), userAnswerProgress);

        GameHistory gameHistory = createGameHistory(currentUser, currentQuestion, answerStatus, userAnswer);
        gameHistoryService.save(gameHistory);
        questionSpecificHistories.add(gameHistory);

        userAnswerProgress = gameProgress.updateCurrentProgress(userAnswerProgress,
                                                                currentQuestion.getAnswer(), userAnswer);

        answerStatus = guess.guess(userAnswer, currentQuestion.getAnswer(), userAnswerProgress);

        gameAnswerDTO.setUserAnswer("");

        return processAnswer(answerStatus, model, currentQuestion, questionSpecificHistories,
                             currentUser, userAnswerProgress);
    }

    private String processAnswer(AnswerStatus answerStatus, Model model, Question currentQuestion,
                                 List<GameHistory> questionSpecificHistories, UserDetails currentUser,
                                 String userAnswerProgress) {
        if (answerStatus == AnswerStatus.NO || answerStatus == AnswerStatus.GUESS_CHARACTER) {
            return setupGameModel(model, currentQuestion, questionSpecificHistories, currentUser, userAnswerProgress);
        }

        if (answerStatus == AnswerStatus.YES) {
            return "success";
        }

        Question newQuestion = questionService.findUniqueUnansweredQuestionForUser(currentUser);
        if (newQuestion == null) {
            model.addAttribute("user", currentUser);
            log.info(currentUser.toString());
            return "game-over";
        }

        userAnswerProgress = gameProgress.initAnswerProgress(newQuestion.getAnswer());
        questionSpecificHistories.clear();
        return setupGameModel(model, newQuestion, questionSpecificHistories, currentUser, userAnswerProgress);
    }

    private String setupGameModel(Model model, Question question,
                                  List<GameHistory> questionSpecificHistories,
                                  UserDetails user, String userAnswerProgress) {
        model.addAttribute("question", question);
        model.addAttribute("questionSpecificHistories", questionSpecificHistories);
        model.addAttribute("user", user);
        model.addAttribute("userAnswerProgress", userAnswerProgress);
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
