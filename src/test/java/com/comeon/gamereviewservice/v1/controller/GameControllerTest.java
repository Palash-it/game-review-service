package com.comeon.gamereviewservice.v1.controller;

import com.comeon.gamereviewservice.enums.EntityStatus;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.service.GameService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GameController.class)
public class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    @Test
    @DisplayName("GetMapping test: This method will take two request param and will return list of games in response")
    void getAllGamesTest() throws Exception {
        List<GameResponse> gameResponses = List.of(GameResponse.builder().id(10l).gameTitle("Test game").status(EntityStatus.ACTIVE.name()).build());
        when(gameService.getAllGames(0, 1)).thenReturn(gameResponses);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/games").param("pageNo", "0").param("pageSize", "1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$.[0].id").value(10l));
    }

}
