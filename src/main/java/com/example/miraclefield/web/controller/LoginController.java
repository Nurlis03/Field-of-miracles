package com.example.miraclefield.web.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@AllArgsConstructor
@Slf4j
public class LoginController {

    @Autowired
    ObjectFactory<HttpSession> httpSessionFactory;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/login-error")
    public String loginError(Model model) {
        HttpSession session = httpSessionFactory.getObject();
        AuthenticationException ex = (AuthenticationException) session.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        String loginError = ex.getMessage().substring(ex.getMessage().indexOf(":") + 1).trim();
        model.addAttribute("loginError", loginError);
        return "login";
    }
}
