package com.example.miraclefield.web.controller;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.UserRepository;
import com.example.miraclefield.service.UserService;
import com.example.miraclefield.web.dto.UserUpdateDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(@AuthenticationPrincipal User user, Model model) {
        return userService.showProfile(user, model);
    }

    @PostMapping("/profile")
    public String editProfile(@AuthenticationPrincipal User user, @ModelAttribute("userUpdateDto") @Valid UserUpdateDto userUpdateDto,
                              BindingResult bindingResult, Model model) {
        return userService.editProfile(user, userUpdateDto, bindingResult, model);
    }

    @GetMapping("/admin/users")
    public String showUsers(@RequestParam(name = "successMessage", required = false) String successMessage, Model model) {
        return userService.showUsers(successMessage, model);
    }

    @PostMapping("/admin/users/lock")
    public String lockUser(@RequestParam("userId") Long userId,
                           @RequestParam("accountNonLocked") boolean accountNonLocked,
                           RedirectAttributes redirectAttributes) {
        return userService.lockUser(userId, accountNonLocked, redirectAttributes);
    }
}
