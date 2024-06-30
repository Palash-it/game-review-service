package com.comeon.gamereviewservice.v1.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerGameInteractionRequestPayload {

    @NotNull
    private Long playerId;
    @NotNull
    private Long gameId;
}
