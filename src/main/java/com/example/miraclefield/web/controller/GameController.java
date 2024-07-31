package com.example.miraclefield.web.controller;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.service.gamelogic.GameService;
import com.example.miraclefield.web.dto.GameAnswerDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/game")
    public String showGameBoard(@AuthenticationPrincipal User user, Model model) {
        return gameService.showGameBoard(user, model);
    }

    @PostMapping("/game")
    public String checkAnswer(@AuthenticationPrincipal User user, @ModelAttribute("gameAnswerDto")
                                @Valid GameAnswerDto gameAnswerDTO, BindingResult bindingResult, Model model) {
        return gameService.checkAnswer(user, gameAnswerDTO, bindingResult, model);
    }
}
