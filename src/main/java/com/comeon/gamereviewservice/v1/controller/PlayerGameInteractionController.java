package com.comeon.gamereviewservice.v1.controller;

import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.dtos.MostLovedGameResponse;
import com.comeon.gamereviewservice.v1.dtos.PlayerGameInteractionRequestPayload;
import com.comeon.gamereviewservice.v1.service.PlayerGameInteractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@Tag(name = "Player and Games Interactions API V1")
@RequestMapping("/api/v1/player-game-interaction")
@RequiredArgsConstructor
public class PlayerGameInteractionController {

    private final PlayerGameInteractionService playerGameInteractionService;

    @PostMapping("/love-game")
    @Operation(summary = "Use this endpoint to love a game. One player can love/unlove one game once")
    public ResponseEntity<String> loveGame(@RequestBody PlayerGameInteractionRequestPayload payload) {
        log.info("PostMapping : v1/player-game-interaction/love-game with param body: {}, pageSize: {}", payload);
        playerGameInteractionService.loveGame(payload);
        return new ResponseEntity<>("Successfully Loved the game", new HttpHeaders(), HttpStatus.CREATED);
    }

    @PostMapping("/unlove-game")
    @Operation(summary = "Use this endpoint to unlove a game. One player can love/unlove one game once")
    public ResponseEntity<String> unLoveGame(@RequestBody PlayerGameInteractionRequestPayload payload) {
        log.info("PostMapping : v1/player-game-interaction/love-game with param body: {}, pageSize: {}", payload);
        playerGameInteractionService.unLoveGame(payload);
        return new ResponseEntity<>("Successfully unloved the game", new HttpHeaders(), HttpStatus.CREATED);
    }

    @GetMapping("/{playerId}/loved-games")
    @Operation(summary = "Get all games that loved by a player")
    public ResponseEntity<List<GameResponse>> getLovedGames(@PathVariable(name = "playerId") Long playerId) {
        log.info("GetMapping : v1/player-game-interaction/{playerId}/loved-games ", playerId);
        List<GameResponse> lovedGames = playerGameInteractionService.getLovedGamesByPlayerId(playerId);
        return new ResponseEntity<>(lovedGames, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/most-loved-games")
    @Operation(summary = "Get most loved games. Use request param [limit] to see Top N number of results ")
    public ResponseEntity<List<MostLovedGameResponse>> getMostLovedGames(@RequestParam(name = "limit", defaultValue = "10") Integer limit) {
        log.info("GetMapping : v1/player-game-interaction/most-loved-games Limit : {}", limit);
        List<MostLovedGameResponse> mostLovedGameResponses = playerGameInteractionService.getMostLovedGames(limit);
        return new ResponseEntity<>(mostLovedGameResponses, new HttpHeaders(), HttpStatus.OK);
    }

}
