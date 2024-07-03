package com.example.miraclefield.controller;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.service.RegistrationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@AllArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/register";
    }

    @GetMapping("/register")
    public String register(Model model) {
        return registrationService.register(model);
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        return registrationService.register(user, bindingResult, model);
    }
}
