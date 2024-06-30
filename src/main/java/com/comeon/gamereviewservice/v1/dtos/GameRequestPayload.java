package com.comeon.gamereviewservice.v1.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameRequestPayload {
    private String gameTitle;
    private String gameDescription;
}
