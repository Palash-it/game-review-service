package com.comeon.gamereviewservice.v1.controller;

import com.comeon.gamereviewservice.v1.dtos.GameRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.dtos.PlayerRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.PlayerResponse;
import com.comeon.gamereviewservice.v1.service.PlayerService;
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
@Tag(name = "Players Information API V1")
@RequestMapping("/api/v1/players")
@RequiredArgsConstructor
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping
    @Operation(summary = "Get All players", description = "This endpoint will provide all players using pageNo and pageSize. Default pageSize is 50")
    public ResponseEntity<List<PlayerResponse>> getAllPlayers(@RequestParam(defaultValue = "0") Integer pageNo,
                                                              @RequestParam(defaultValue = "50") Integer pageSize) {
        log.info("GetMapping : v1/players with param pageNo: {}, pageSize: {}", pageNo, pageSize);
        List<PlayerResponse> players = playerService.getAllPlayers(pageNo, pageSize);

        return new ResponseEntity<>(players, new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get a single player by player id", description = "This endpoint will provide a single entity by id")
    public ResponseEntity<PlayerResponse> getSinglePlayer(@PathVariable("id") Long playerId) {
        log.info("GetMapping : v1/players/{} ", playerId);
        PlayerResponse playerResponse = playerService.getPlayerById(playerId);

        return new ResponseEntity<>(playerResponse, new HttpHeaders(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Create a new Player. Player emailAddress is unique")
    public ResponseEntity<?> createNewPlayer(@RequestBody @Valid PlayerRequestPayload requestPayload) {
        log.info("PostMapping:  v1/players with payload: {}", requestPayload);
        PlayerResponse playerResponse = playerService.addNewPlayer(requestPayload);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(playerResponse.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }
}
