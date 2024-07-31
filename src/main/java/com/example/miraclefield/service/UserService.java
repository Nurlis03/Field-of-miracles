package com.example.miraclefield.service;

import com.example.miraclefield.entity.Point;
import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.PointRepository;
import com.example.miraclefield.repository.RoleRepository;
import com.example.miraclefield.repository.UserRepository;
import com.example.miraclefield.web.dto.UserDto;
import com.example.miraclefield.web.dto.UserUpdateDto;
import com.example.miraclefield.web.error.UserAlreadyExistException;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class UserService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private PointRepository pointRepository;
    public void registerNewUserAccount(UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setBirthDate(accountDto.getBirthDate());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));

        Point userPoint = new Point();
        pointRepository.save(userPoint);
        user.setPoint(userPoint);

        userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }

    public void updateUser(User user, UserUpdateDto userUpdateDto) {

        if (emailExists(userUpdateDto.getEmail()) && !Objects.equals(userUpdateDto.getEmail(), user.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + userUpdateDto.getEmail());
        }

        user.setFirstName(userUpdateDto.getFirstName());
        user.setMiddleName(userUpdateDto.getMiddleName());
        user.setLastName(userUpdateDto.getLastName());
        user.setBirthDate(userUpdateDto.getBirthDate());
        user.setEmail(userUpdateDto.getEmail());

        userRepository.save(user);
    }

    public String showProfile(User user, Model model) {
        UserUpdateDto userUpdateDto = UserUpdateDto.builder()
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .birthDate(user.getBirthDate())
                .build();
        model.addAttribute("isAdmin", user.hasRole("ROLE_ADMIN"));
        model.addAttribute("userUpdateDto", userUpdateDto);
        model.addAttribute("userPoints", user.getPoint().getAmount());
        return "user-profile";
    }

    public String editProfile(User user, UserUpdateDto userUpdateDto,
                              BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "user-profile";
        }
        try {
            updateUser(user, userUpdateDto);
            model.addAttribute("isAdmin", user.hasRole("ROLE_ADMIN"));
        }
        catch (UserAlreadyExistException e) {
            model.addAttribute("emailErrorMessage", e.getMessage());
            return "user-profile";
        }
        model.addAttribute("successMessage", "Profile updated successfully!");
        return "user-profile";
    }

    public String showUsers(String successMessage, Model model) {
        List<User> users = userRepository.findAll();
        model.addAttribute("users", users);
        if (successMessage != null) {
            model.addAttribute("successMessage", successMessage);
        }
        return "admin/users";
    }

    public String lockUser(Long userId, boolean accountNonLocked, RedirectAttributes redirectAttributes) {
        User user = userRepository.findById(userId).orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + userId));
        user.setAccountNonLocked(accountNonLocked);
        userRepository.save(user);
        String message = "User with email " + user.getEmail() + " " + (accountNonLocked ? "unlocked" : "locked");
        redirectAttributes.addAttribute("successMessage", message);
        return "redirect:/admin/users";
    }
}
