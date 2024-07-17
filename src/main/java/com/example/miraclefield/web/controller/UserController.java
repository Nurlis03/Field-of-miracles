package com.example.miraclefield.web.controller;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.UserRepository;
import com.example.miraclefield.service.UserService;
import com.example.miraclefield.web.dto.UserUpdateDto;
import com.example.miraclefield.web.error.UserAlreadyExistException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@AllArgsConstructor
public class UserController {
    private UserRepository userRepository;

    private UserService userService;

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
        model.addAttribute("isAdmin", user.hasRole("ROLE_ADMIN"));
        model.addAttribute("userUpdateDto", userUpdateDto);
        return "user-profile";
    }

    @PostMapping("/profile")
    public String editProfile(@ModelAttribute("userUpdateDto") @Valid UserUpdateDto userUpdateDto,
                              BindingResult bindingResult, @RequestParam("isAdmin") boolean isAdmin, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-profile";
        }
        try {
            userService.updateUser(userUpdateDto);
            log.info("Update profile of user with information: {}", userUpdateDto.toString());
        }
        catch (UserAlreadyExistException uaeEx) {
            model.addAttribute("emailErrorMessage", uaeEx.getMessage());
            model.addAttribute("isAdmin", isAdmin);
            return "user-profile";
        } catch (RuntimeException ex) {
            log.warn("Unable to update user", ex);
        }
        model.addAttribute("successMessage", "Profile updated successfully!");
        model.addAttribute("isAdmin", isAdmin);
        return "user-profile";
    }

    @GetMapping("/admin/users")
    public String showUsers(Model model, @RequestParam(name = "successMessage", required = false) String successMessage) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        return "admin/users";
    }

    @PostMapping("/admin/users/lock")
    public String lockUser(@RequestParam("userId") Long userId,
                           @RequestParam("accountNonLocked") boolean accountNonLocked,
                           RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        user.setAccountNonLocked(accountNonLocked);
        userRepository.save(user);
        String message = "User with email " + user.getEmail() + " " + (accountNonLocked ? "unlocked" : "locked");
        redirectAttributes.addAttribute("successMessage", message);
        return "redirect:/admin/users";
    }
}
