package com.comeon.gamereviewservice.validator;

import com.comeon.gamereviewservice.v1.repository.GameRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class GameTitleIsUniqueValidator implements ConstraintValidator<GameTitleIsUnique, String> {

    private final GameRepository gameRepository;

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(fieldValue)) {
            setCustomMessageForValidation(constraintValidatorContext, "gameTitle is required and should not be empty/null");
            return false;
        }

        return !gameRepository.existsByTitle(fieldValue);
    }

    private void setCustomMessageForValidation(ConstraintValidatorContext constraintContext, String message) {
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
