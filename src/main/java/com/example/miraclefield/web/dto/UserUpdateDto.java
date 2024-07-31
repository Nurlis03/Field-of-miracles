package com.example.miraclefield.web.dto;

import com.example.miraclefield.validation.ValidEmail;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@Builder
public class UserUpdateDto {
    @NotBlank(message = "First Name should not be empty")
    private String firstName;

    @NotBlank(message = "Last Name should not be empty")
    private String lastName;

    private String middleName;

    @NotNull(message = "Birth Date should not be empty")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date birthDate;

    @ValidEmail
    private String email;
}
