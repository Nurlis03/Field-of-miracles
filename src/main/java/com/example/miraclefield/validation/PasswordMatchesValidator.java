package com.example.miraclefield.validation;

import com.example.miraclefield.web.dto.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        final UserDto user = (UserDto) obj;
        boolean valid = user.getPassword().equals(user.getMatchingPassword());

        if (!valid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Passwords don't match")
                    .addPropertyNode("matchingPassword")
                    .addConstraintViolation();
        }

        return valid;
    }
}
