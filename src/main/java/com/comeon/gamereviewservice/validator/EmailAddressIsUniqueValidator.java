package com.comeon.gamereviewservice.validator;

import com.comeon.gamereviewservice.v1.repository.PlayerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

@RequiredArgsConstructor
public class EmailAddressIsUniqueValidator implements ConstraintValidator<EmailAddressIsUnique, String> {

    private final PlayerRepository playerRepository;

    @Override
    public boolean isValid(String fieldValue, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(fieldValue)) {
            setCustomMessageForValidation(constraintValidatorContext, "Email address is required and should not be empty/null");
            return false;
        }

        return !playerRepository.existsByEmail(fieldValue);
    }

    private void setCustomMessageForValidation(ConstraintValidatorContext constraintContext, String message) {
        constraintContext.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}
