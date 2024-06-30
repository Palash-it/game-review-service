package com.comeon.gamereviewservice.v1.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MostLovedGameResponse {
    private Long gameId;
    private Long numberOfLoves;

}
