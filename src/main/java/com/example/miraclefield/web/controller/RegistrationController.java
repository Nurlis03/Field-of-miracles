package com.example.miraclefield.web.controller;

import com.example.miraclefield.service.RegistrationService;
import com.example.miraclefield.web.dto.UserDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
public class RegistrationController {
    private RegistrationService registrationService;

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/registration";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        model.addAttribute("userDto", UserDto.builder().build());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDto") @Valid UserDto userDto,
                               BindingResult bindingResult, Model model) {
        return registrationService.registerUser(userDto, bindingResult, model);
    }
}
