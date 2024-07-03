package com.example.miracle_field.controller;

import com.example.miracle_field.dto.GameAnswerDTO;
import com.example.miracle_field.service.game_logic.GameService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
@Controller
@AllArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping("/game")
    public String showGameBoard(Principal principal, Model model) {
        return gameService.showGameBoard(principal, model);
    }

    @PostMapping("/game")
    public String checkAnswer(@ModelAttribute("gameAnswerDTO") @Valid GameAnswerDTO gameAnswerDTO,
                              BindingResult bindingResult, Model model, Principal principal) {
        return gameService.checkAnswer(gameAnswerDTO, bindingResult, model, principal);
    }
}
