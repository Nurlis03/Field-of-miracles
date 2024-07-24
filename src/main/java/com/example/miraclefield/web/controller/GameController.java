package com.example.miraclefield.web.controller;

import com.example.miraclefield.web.dto.GameAnswerDto;
import com.example.miraclefield.service.gamelogic.GameService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/game")
    public String showGameBoard(Model model) {
        return gameService.showGameBoard(model);
    }

    @PostMapping("/game")
    public String checkAnswer(@ModelAttribute("gameAnswerDTO") @Valid GameAnswerDto gameAnswerDTO,
                              BindingResult bindingResult, Model model) {
        return gameService.checkAnswer(gameAnswerDTO, bindingResult, model);
    }
}
