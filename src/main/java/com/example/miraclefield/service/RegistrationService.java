package com.example.miraclefield.service;

import com.example.miraclefield.entity.User;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationService {

    private UserService userService;

    public String register(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    public String register(@ModelAttribute("user") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("usernameError", "Username already exists");
            return "register";
        }

        userService.save(user);
        log.info("Saving user " + user.getUsername() + " with role " + user.getRole());
        return "redirect:/login";
    }
}
