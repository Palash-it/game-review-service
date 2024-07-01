package com.comeon.gamereviewservice.v1.dtos;

import com.comeon.gamereviewservice.validator.GameTitleIsUnique;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameRequestPayload {
    @GameTitleIsUnique( message = "Game title is already in use")
    private String gameTitle;
    private String gameDescription;
}
