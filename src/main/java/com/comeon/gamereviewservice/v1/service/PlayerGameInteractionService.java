package com.comeon.gamereviewservice.v1.service;

import com.comeon.gamereviewservice.exceptions.ValidationException;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.dtos.MostLovedGameResponse;
import com.comeon.gamereviewservice.v1.dtos.PlayerGameInteractionRequestPayload;
import com.comeon.gamereviewservice.v1.mapper.GameMapper;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import com.comeon.gamereviewservice.v1.model.PlayerGameInteractionEntity;
import com.comeon.gamereviewservice.v1.repository.PlayerGameInteractionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PlayerGameInteractionService {

    private final PlayerGameInteractionRepository playerGameInteractionRepository;
    private final PlayerService playerService;
    private final GameService gameService;
    private final GameMapper gameMapper;

    /**
     * Make sure this player did not love the game before. If the player loved the game already and try to do same action then throw exception with a clear message
     * If the player unloved the game before and now changed his mind and want to love then update the isLove value from false to true.
     * Is there any duration to change mind to unLove a game after love once? Did not ask for it now
     *
     * @param payload
     * @return
     */

    public void loveGame(PlayerGameInteractionRequestPayload payload) {
        GameEntity gameEntity = gameService.getGameEntityById(payload.getGameId());
        PlayerEntity playerEntity = playerService.getPlayerEntityById(payload.getPlayerId());

        if (playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity)) {
            throw new ValidationException("You have already loved this game!");
        }
        playerGameInteractionRepository.findByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity).ifPresentOrElse(entity -> {
            entity.setIsLoved(true);
            playerGameInteractionRepository.save(entity);
            log.info("PlayerId : {}, unloved gameId : {} on {}, Changing his mind to love the game again on : {}", entity.getPlayer().getPlayerId(), entity.getGame().getGameId(), entity.getCreatedAt(), entity.getLastModifiedAt());
        }, () -> {
            PlayerGameInteractionEntity playerGameInteractionEntity = PlayerGameInteractionEntity.builder().player(playerEntity).game(gameEntity).isLoved(true).build();
            playerGameInteractionRepository.save(playerGameInteractionEntity);
            log.info("PlayerId : {}, loved gameId : {}", playerGameInteractionEntity.getPlayer().getPlayerId(), playerGameInteractionEntity.getGame().getGameId());
        });
    }

    /**
     * Make sure this player did not unLove the game before. If the player unLoved the game already and try to do same action then throw exception with a clear message
     * If the player loved the game before and now changed his mind and want to unLove then update the isLove value from true to false.
     * Is there any duration to change mind to unLove a game after love once? Did not ask for it now
     *
     * @param payload
     * @return
     */
    public void unLoveGame(PlayerGameInteractionRequestPayload payload) {
        GameEntity gameEntity = gameService.getGameEntityById(payload.getGameId());
        PlayerEntity playerEntity = playerService.getPlayerEntityById(payload.getPlayerId());

        if (playerGameInteractionRepository.existsByPlayerAndGameAndIsLovedFalse(playerEntity, gameEntity)) {
            throw new ValidationException("You have already unLoved this game!");
        }
        playerGameInteractionRepository.findByPlayerAndGameAndIsLovedTrue(playerEntity, gameEntity).ifPresentOrElse(entity -> {
            entity.setIsLoved(false);
            playerGameInteractionRepository.save(entity);
            log.info("PlayerId : {}, loved gameId : {} on {}, Changing his mind to unLove the game again on : {}", entity.getPlayer().getPlayerId(), entity.getGame().getGameId(), entity.getCreatedAt(), entity.getLastModifiedAt());
        }, () -> {
            PlayerGameInteractionEntity playerGameInteractionEntity = PlayerGameInteractionEntity.builder().player(playerEntity).game(gameEntity).isLoved(false).build();
            playerGameInteractionRepository.save(playerGameInteractionEntity);
            log.info("PlayerId : {}, unLoved gameId : {}", playerGameInteractionEntity.getPlayer().getPlayerId(), playerGameInteractionEntity.getGame().getGameId());
        });
    }

    public List<GameResponse> getLovedGamesByPlayerId(Long playerId) {
        PlayerEntity playerEntity = playerService.getPlayerEntityById(playerId);
        return Optional.ofNullable(playerGameInteractionRepository.findByPlayerAndIsLovedTrue(playerEntity))
                .orElse(Collections.emptyList())
                .stream()
                .map(PlayerGameInteractionEntity::getGame)
                .map(game -> gameMapper.gameEntityToGameDto(game))
                .toList();
    }

    /**
     * @param limit max number of records should be fetched
     * @return
     */
    public List<MostLovedGameResponse> getMostLovedGames(Integer limit) {
        Pageable pageable = PageRequest.of(0, limit);
        return Optional.ofNullable(playerGameInteractionRepository.getMostLovedGames(pageable)).orElse(Collections.emptyList())
                .stream().map(obj -> MostLovedGameResponse.builder()
                        .gameId((Long) obj[0])
                        .numberOfLoves((Long) obj[1])
                        .build()).toList();
    }
}
