package com.comeon.gamereviewservice.v1.dtos;

import com.comeon.gamereviewservice.validator.EmailAddressIsUnique;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerRequestPayload {
    @NotBlank(message = "Player's first name is required")
    @NotNull(message = "Player's first name is required")
    private String fname;
    private String lname;
    @Email(message = "Email Address is invalid!")
    @EmailAddressIsUnique(message = "Email address is already in use")
    private String emailAddress;
}
