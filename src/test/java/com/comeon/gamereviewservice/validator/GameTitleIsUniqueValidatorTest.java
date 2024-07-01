package com.comeon.gamereviewservice.validator;

import com.comeon.gamereviewservice.v1.repository.GameRepository;
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
class GameTitleIsUniqueValidatorTest {

    @Mock
    private GameRepository gameRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private GameTitleIsUniqueValidator gameTitleIsUniqueValidator;

    @Test
    @DisplayName("Test isValid should return true when game title is not already in use")
    void isValidTrueTest() {
        String inputGameTitle = "candy crush saga";
        when(gameRepository.existsByTitle(inputGameTitle)).thenReturn(false);
        boolean isValid = gameTitleIsUniqueValidator.isValid(inputGameTitle, context);
        assertTrue(isValid);
    }

    @Test
    @DisplayName("Test isValid should return false when game title is already in use")
    void isValidFalseTest() {
        String inputGameTitle = "candy crush saga";
        when(gameRepository.existsByTitle(inputGameTitle)).thenReturn(true);
        boolean isValid = gameTitleIsUniqueValidator.isValid(inputGameTitle, context);
        assertFalse(isValid);
    }
}
