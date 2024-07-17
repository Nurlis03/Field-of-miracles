package com.example.miraclefield.security;

import com.example.miraclefield.entity.User;
import com.example.miraclefield.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Transactional
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(final String email) throws UsernameNotFoundException {

        try {
            final User user = userRepository.findByEmail(email);
            if (user == null) {
                log.error("No user found with email: " + email);
                throw new UsernameNotFoundException("No user found with email: " + email);
            }
            return user;
        } catch (final Exception e) {
            throw new RuntimeException(e);
        }
    }
}
