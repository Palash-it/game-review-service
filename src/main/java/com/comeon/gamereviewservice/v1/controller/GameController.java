package com.comeon.gamereviewservice.v1.controller;

import com.comeon.gamereviewservice.v1.dtos.GameRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.service.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@Slf4j
@Tag(name = "Games Information API V1")
@RequestMapping("/api/v1/games")
@RequiredArgsConstructor
public class GameController {

    private final GameService gameService;

    @GetMapping
    @Operation(summary = "Get All games", description = "This endpoint will provide all games using pageNo and pageSize. Default pageSize is 50")
    public ResponseEntity<List<GameResponse>> getAllGames(@RequestParam(defaultValue = "0") Integer pageNo,
                                                          @RequestParam(defaultValue = "50") Integer pageSize) {
        log.info("GetMapping : v1/games with param pageNo: {}, pageSize: {}", pageNo, pageSize);
        List<GameResponse> games = gameService.getAllGames(pageNo, pageSize);

        return new ResponseEntity<>(games, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Game. Game title is unique")
    public ResponseEntity<URI> createGame(@RequestBody @Valid GameRequestPayload requestPayload) {
        log.info("PostMapping:  v1/games with payload: {}", requestPayload);
        GameResponse gameResponse = gameService.addNewGame(requestPayload);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(gameResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a game by game id", description = "This endpoint will provide a single entity by id")
    public ResponseEntity<GameResponse> getSingleGame(@PathVariable("id") Long gameId) {
        log.info("GetMapping : v1/games/{} ", gameId);
        GameResponse gameResponse = gameService.getGameById(gameId);

        return new ResponseEntity<>(gameResponse, new HttpHeaders(), HttpStatus.OK);
    }
}
