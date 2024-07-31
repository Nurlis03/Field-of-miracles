package com.example.miraclefield.service;

import com.example.miraclefield.web.dto.LoginDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
@AllArgsConstructor
public class LoginService {

    private AuthenticationManager authManager;
    public String loginUser(LoginDto loginDto, BindingResult bindingResult,
                          Model model, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login";
        }
        try {
            UsernamePasswordAuthenticationToken authReq
                    = new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword());
            Authentication auth = authManager.authenticate(authReq);

            SecurityContext sc = SecurityContextHolder.getContext();
            sc.setAuthentication(auth);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
            return "redirect:/game";
        }
        catch (AuthenticationException e) {
            model.addAttribute("loginError", e.getMessage());
        }
        return "login";
    }
}
