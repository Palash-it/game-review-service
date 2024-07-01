package com.comeon.gamereviewservice.v1.service;

import com.comeon.gamereviewservice.enums.EntityStatus;
import com.comeon.gamereviewservice.exceptions.ValidationException;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.dtos.PlayerGameInteractionRequestPayload;
import com.comeon.gamereviewservice.v1.mapper.GameMapper;
import com.comeon.gamereviewservice.v1.mapper.GameMapperImpl;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import com.comeon.gamereviewservice.v1.model.PlayerGameInteractionEntity;
import com.comeon.gamereviewservice.v1.repository.PlayerGameInteractionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PlayerGameInteractionServiceTest {

    @Mock
    private PlayerGameInteractionRepository playerGameInteractionRepository;
    @Mock
    private PlayerService playerService;
    @Mock
    private GameService gameService;

    @InjectMocks
    private PlayerGameInteractionService service;

    @Spy
    private GameMapper gameMapper = new GameMapperImpl();


    @Test
    @DisplayName("Game Love Test: When game is already loved by the player it should throw the exception")
    void loveGameTest_When_AlreadyLoved() {
        PlayerGameInteractionRequestPayload payload = PlayerGameInteractionRequestPayload.builder().playerId(1l).gameId(1l).build();
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(1l).build();
        GameEntity gameEntity = GameEntity.builder().gameId(1l).build();
        when(gameService.getGameEntityById(payload.getGameId())).thenReturn(gameEntity);
        when(playerService.getPlayerEntityById(payload.getPlayerId())).thenReturn(playerEntity);

        when(playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity)).thenReturn(true);
        ValidationException validationException = assertThrows(ValidationException.class, () -> service.loveGame(payload));
        assertEquals("You have already loved this game!", validationException.getMessage());
    }

    @Test
    @DisplayName("Game Love Test: When this game is not loved/unloved by the player means should occur a fresh entry")
    void loveGameTest() {
        PlayerGameInteractionRequestPayload payload = PlayerGameInteractionRequestPayload.builder().playerId(1l).gameId(1l).build();
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(1l).build();
        GameEntity gameEntity = GameEntity.builder().gameId(1l).build();
        when(gameService.getGameEntityById(payload.getGameId())).thenReturn(gameEntity);
        when(playerService.getPlayerEntityById(payload.getPlayerId())).thenReturn(playerEntity);

        when(playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity)).thenReturn(false);
        when(playerGameInteractionRepository.findByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity)).thenReturn(Optional.empty());

        PlayerGameInteractionEntity playerGameInteractionEntity = PlayerGameInteractionEntity.builder().player(playerEntity).game(gameEntity).isLoved(true).build();
        service.loveGame(payload);
        verify(playerGameInteractionRepository, times(1)).save(playerGameInteractionEntity);
    }

    @Test
    @DisplayName("Game Love Test: When this game unLoved before and changed mind to love it")
    void loveAUnLovedGameTest() {
        PlayerGameInteractionRequestPayload payload = PlayerGameInteractionRequestPayload.builder().playerId(1l).gameId(1l).build();
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(1l).build();
        GameEntity gameEntity = GameEntity.builder().gameId(1l).build();
        when(gameService.getGameEntityById(payload.getGameId())).thenReturn(gameEntity);
        when(playerService.getPlayerEntityById(payload.getPlayerId())).thenReturn(playerEntity);

        when(playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity)).thenReturn(false);

        PlayerGameInteractionEntity playerGameInteractionEntity = PlayerGameInteractionEntity.builder().interactionId(1l).player(playerEntity).game(gameEntity).isLoved(false).build();
        when(playerGameInteractionRepository.findByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity)).thenReturn(Optional.of(playerGameInteractionEntity));

        service.loveGame(payload);
        verify(playerGameInteractionRepository, times(1)).save(playerGameInteractionEntity);
    }

    @Test
    @DisplayName("Game Love Test: When game is already loved by the player it should throw the exception")
    void unLoveGameTest_When_AlreadyUnLoved() {
        PlayerGameInteractionRequestPayload payload = PlayerGameInteractionRequestPayload.builder().playerId(1l).gameId(1l).build();
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(1l).build();
        GameEntity gameEntity = GameEntity.builder().gameId(1l).build();
        when(gameService.getGameEntityById(payload.getGameId())).thenReturn(gameEntity);
        when(playerService.getPlayerEntityById(payload.getPlayerId())).thenReturn(playerEntity);

        when(playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity)).thenReturn(true);
        ValidationException validationException = assertThrows(ValidationException.class, () -> service.unLoveGame(payload));
        assertEquals("You have already unLoved this game!", validationException.getMessage());
    }


    @Test
    @DisplayName("Game unLove Test: When this game is not loved/unloved by the player means should occur a fresh entry")
    void unLoveGameTest() {
        PlayerGameInteractionRequestPayload payload = PlayerGameInteractionRequestPayload.builder().playerId(1l).gameId(1l).build();
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(1l).build();
        GameEntity gameEntity = GameEntity.builder().gameId(1l).build();
        when(gameService.getGameEntityById(payload.getGameId())).thenReturn(gameEntity);
        when(playerService.getPlayerEntityById(payload.getPlayerId())).thenReturn(playerEntity);

        when(playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity)).thenReturn(false);
        when(playerGameInteractionRepository.findByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity)).thenReturn(Optional.empty());

        PlayerGameInteractionEntity playerGameInteractionEntity = PlayerGameInteractionEntity.builder().player(playerEntity).game(gameEntity).isLoved(false).build();
        service.unLoveGame(payload);
        verify(playerGameInteractionRepository, times(1)).save(playerGameInteractionEntity);
    }

    @Test
    @DisplayName("Game unLove Test: When this game loved before and changed mind to unLove it")
    void unLoveALovedGameTest() {
        PlayerGameInteractionRequestPayload payload = PlayerGameInteractionRequestPayload.builder().playerId(1l).gameId(1l).build();
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(1l).build();
        GameEntity gameEntity = GameEntity.builder().gameId(1l).build();
        when(gameService.getGameEntityById(payload.getGameId())).thenReturn(gameEntity);
        when(playerService.getPlayerEntityById(payload.getPlayerId())).thenReturn(playerEntity);

        when(playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity)).thenReturn(false);

        PlayerGameInteractionEntity playerGameInteractionEntity = PlayerGameInteractionEntity.builder().interactionId(1l).player(playerEntity).game(gameEntity).isLoved(true).build();
        when(playerGameInteractionRepository.findByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity)).thenReturn(Optional.of(playerGameInteractionEntity));

        service.unLoveGame(payload);
        verify(playerGameInteractionRepository, times(1)).save(playerGameInteractionEntity);
    }


    @Test
    @DisplayName("Get loved games list by player id test. If a played does not have any loved game then it should return a empty list")
    void getLovedGamesByPlayerIdTest() {
        Long playerId = 10l;
        PlayerEntity playerEntity = PlayerEntity.builder().playerId(playerId).build();
        when(playerService.getPlayerEntityById(playerId)).thenReturn(playerEntity);
        PlayerGameInteractionEntity game1 = PlayerGameInteractionEntity.builder().player(playerEntity).game(GameEntity.builder().gameId(1l).status(EntityStatus.ACTIVE).title("Call of duty").build()).build();
        List<PlayerGameInteractionEntity> playerGameInteractionEntityList = List.of(game1);
        when(playerGameInteractionRepository.findByPlayerAndIsLovedTrue(playerEntity)).thenReturn(playerGameInteractionEntityList);
        List<GameResponse> lovedGames = service.getLovedGamesByPlayerId(playerId);
        assertEquals(1, lovedGames.size());
        assertEquals("Call of duty", lovedGames.get(0).getGameTitle());
    }

}
