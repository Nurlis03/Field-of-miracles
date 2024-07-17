package com.example.miraclefield.web.controller;

import com.example.miraclefield.service.UserService;
import com.example.miraclefield.web.dto.UserDto;
import com.example.miraclefield.web.error.UserAlreadyExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.validation.Valid;

@Controller
@Slf4j
public class RegistrationController {
    private final UserService userService;

    @Autowired
    public RegistrationController(UserService userService, MessageSource messageSource) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model) {
        return "redirect:/registration";
    }

    @GetMapping("/registration")
    public String showRegistrationForm(Model model) {
        log.info("Rendering registration page.");
        model.addAttribute("userDto", UserDto.builder().build());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userDto") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        log.info("Registering user account with information: {}", userDto.toString());
        try {
            userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("emailErrorMessage", uaeEx.getMessage());
            return "registration";
        } catch (RuntimeException ex) {
            log.warn("Unable to register user", ex);
            return "registration";
        }
        return "login";
    }
}
