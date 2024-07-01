package com.comeon.gamereviewservice.validator;

import com.comeon.gamereviewservice.v1.repository.PlayerRepository;
import jakarta.validation.ConstraintValidatorContext;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EmailAddressIsUniqueValidatorTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private EmailAddressIsUniqueValidator emailAddressIsUniqueValidator;

    @Test
    @DisplayName("Test isValid should return true when email is not already in use")
    void isValidTrueTest() {
        String inputEmail = "test@test.com";
        when(playerRepository.existsByEmail(inputEmail)).thenReturn(false);
        boolean isValid = emailAddressIsUniqueValidator.isValid(inputEmail, context);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Test isValid should return false when email is already in use")
    void isValidFalseTest() {
        String inputEmail = "test@test.com";
        when(playerRepository.existsByEmail(inputEmail)).thenReturn(true);
        boolean isValid = emailAddressIsUniqueValidator.isValid(inputEmail, context);
        assertFalse(isValid);
    }
}
