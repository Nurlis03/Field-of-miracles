package com.example.miraclefield.service;

import com.example.miraclefield.repository.RoleRepository;
import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.UserRepository;
import com.example.miraclefield.web.dto.UserDto;
import com.example.miraclefield.web.dto.UserUpdateDto;
import com.example.miraclefield.web.error.UserAlreadyExistException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Objects;

@Service
@Slf4j
@AllArgsConstructor
@Transactional
public class UserService {
    private RoleRepository roleRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    public void registerNewUserAccount(final UserDto accountDto) {
        if (emailExists(accountDto.getEmail())) {
            throw new UserAlreadyExistException("There is an account with that email address: " + accountDto.getEmail());
        }
        final User user = new User();

        user.setFirstName(accountDto.getFirstName());
        user.setLastName(accountDto.getLastName());
        user.setBirthDate(accountDto.getBirthDate());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setEmail(accountDto.getEmail());
        user.setRoles(Collections.singletonList(roleRepository.findByName("ROLE_USER")));
        userRepository.save(user);
    }

    public boolean emailExists(String email) {
        return userRepository.findByEmail(email) != null;
    }

    public void updateUser(UserUpdateDto userUpdateDto) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

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
}
