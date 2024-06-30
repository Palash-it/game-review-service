package com.comeon.gamereviewservice.v1.controller;

import com.comeon.gamereviewservice.v1.dtos.PlayerGameInteractionRequestPayload;
import com.comeon.gamereviewservice.v1.service.PlayerGameInteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@Tag(name = "Player and Games Interactions API V1")
@RequestMapping("/api/v1/player-game-interaction")
@RequiredArgsConstructor
public class PlayerGameInteractionController {

    private final PlayerGameInteractionService playerGameInteractionService;

    @PostMapping("/love-game")
    @Operation(summary = "Use this endpoint to love a game. One player can love/unlove one game once")
    public ResponseEntity<?> loveGame(@RequestBody PlayerGameInteractionRequestPayload payload) {
        log.info("PostMapping : v1/player-game-interaction/love-game with param body: {}, pageSize: {}", payload);
        playerGameInteractionService.loveGame(payload);
        return new ResponseEntity<>("Successfully Loved the game", new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/unlove-game")
    @Operation(summary = "Use this endpoint to unlove a game. One player can love/unlove one game once")
    public ResponseEntity<?> unLoveGame(@RequestBody PlayerGameInteractionRequestPayload payload) {
        log.info("PostMapping : v1/player-game-interaction/love-game with param body: {}, pageSize: {}", payload);
        playerGameInteractionService.unLoveGame(payload);
        return new ResponseEntity<>("Successfully unloved the game", new HttpHeaders(), HttpStatus.CREATED);
    }

}
