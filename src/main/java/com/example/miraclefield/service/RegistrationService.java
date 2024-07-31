package com.example.miraclefield.service;

import com.example.miraclefield.web.dto.UserDto;
import com.example.miraclefield.web.error.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class RegistrationService {
    private UserService userService;

    public String registerUser(UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        try {
            userService.registerNewUserAccount(userDto);
        } catch (UserAlreadyExistException e) {
            model.addAttribute("emailErrorMessage", e.getMessage());
            return "registration";
        }
        return "redirect:/login";
    }
}
