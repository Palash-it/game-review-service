package com.comeon.gamereviewservice.v1.service;

import com.comeon.gamereviewservice.exceptions.ResourceNotFoundException;
import com.comeon.gamereviewservice.exceptions.ValidationException;
import com.comeon.gamereviewservice.v1.dtos.GameRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.mapper.GameMapper;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import com.comeon.gamereviewservice.v1.repository.GameRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;
    private final GameMapper gameMapper;

    public List<GameResponse> getAllGames(Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("title").ascending());

        Slice<GameEntity> slicedResult = gameRepository.findBy(paging);
        if (slicedResult.hasContent()) {
            return slicedResult.getContent().stream().map(gameMapper::gameEntityToGameDto).toList();
        }
        return Collections.emptyList();
    }

    public GameResponse getGameById(Long gameId) {
        GameEntity gameEntity = getGameEntityById(gameId);
        return gameMapper.gameEntityToGameDto(gameEntity);
    }

    protected GameEntity getGameEntityById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new ResourceNotFoundException("No game match was found by game id [" + gameId + "]"));
    }

    /**
     * Game title is unique, make sure it is not already exist.
     * If Game title is exist then throw exception with proper message
     *
     * @param requestPayload
     * @return
     */
    public GameResponse addNewGame(GameRequestPayload requestPayload) {
        if (gameRepository.existsByTitle(requestPayload.getGameTitle()))
            throw new ValidationException("Game Title [{" + requestPayload.getGameTitle() + "}] is already in use");
        GameEntity gameEntity = gameMapper.gameRequestPayloadToGameEntity(requestPayload);
        GameEntity persistEntity = gameRepository.save(gameEntity);
        return gameMapper.gameEntityToGameDto(persistEntity);
    }

}
