package com.example.miraclefield.web.controller;


import com.example.miraclefield.service.LoginService;
import com.example.miraclefield.web.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class LoginController {

    private LoginService loginService;

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("loginDto", LoginDto.builder().build());
        return "login";
    }

    @PostMapping("/login")
    public String loginProcess(@ModelAttribute("loginDto") @Valid LoginDto loginDto,
                             BindingResult bindingResult, Model model, HttpServletRequest request) {
        return loginService.loginUser(loginDto, bindingResult, model, request);
    }
}
