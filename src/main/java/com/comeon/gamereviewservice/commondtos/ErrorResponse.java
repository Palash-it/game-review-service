package com.comeon.gamereviewservice.commondtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer code;
    private String message;
    private String description;
    private String path;
}
