package com.comeon.gamereviewservice.v1.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PlayerRequestPayload {
    private String fname;
    private String lname;
    private String emailAddress;
    private String status;
}