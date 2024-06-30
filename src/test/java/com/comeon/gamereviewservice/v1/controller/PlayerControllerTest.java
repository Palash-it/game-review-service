package com.comeon.gamereviewservice.v1.controller;

import com.comeon.gamereviewservice.enums.EntityStatus;
import com.comeon.gamereviewservice.v1.dtos.PlayerRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.PlayerResponse;
import com.comeon.gamereviewservice.v1.service.PlayerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PlayerController.class)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PlayerService playerService;

    @Test
    @DisplayName("GetMapping test: This method will take two request param and will return list of players in response")
    void getAllPlayersTest() throws Exception {
        List<PlayerResponse> playerResponses = List.of(PlayerResponse.builder().id(10l).fname("Palash").status(EntityStatus.ACTIVE.name()).build());
        when(playerService.getAllPlayers(0, 1)).thenReturn(playerResponses);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/players").param("pageNo", "0").param("pageSize", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(10l));
    }


    @Test
    @DisplayName("PostMapping Test: This method will take requestbody with required param, will validate the payload and will create a new entry")
    void createNewPlayerTest() throws Exception {
        PlayerRequestPayload playerRequestPayload = PlayerRequestPayload.builder().fname("Palash").build();
        when(playerService.addNewPlayer(playerRequestPayload)).thenReturn(PlayerResponse.builder().id(10l).fname("Palash").build());
        ObjectMapper objectMapper = new ObjectMapper();
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/players").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(playerRequestPayload)))
                .andExpect(status().isCreated());
    }
}
