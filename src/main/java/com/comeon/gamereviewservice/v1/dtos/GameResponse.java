package com.comeon.gamereviewservice.v1.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameResponse {
    private Long id;
    private String gameTitle;
    private String gameDescription;
    private String status;

}
