package com.example.miraclefield.config;

import com.example.miraclefield.repository.UserRepository;
import com.example.miraclefield.security.CustomUserDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig {
    private final UserRepository userRepository;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(Customizer.withDefaults()).authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/admin/**").hasAnyAuthority("ROLE_ADMIN")
                        .requestMatchers("/registration", "/login", "/css/**").permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/game", true)
                        .failureUrl("/login-error")
                        .permitAll()
                )
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessUrl("/login?logout")
                );

        return http.build();
    }

    @Bean
    public CustomUserDetailsService customUserDetailsService() {
        return new CustomUserDetailsService(userRepository);
    }
}
