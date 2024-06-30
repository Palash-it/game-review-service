package com.comeon.gamereviewservice.v1.service;

import com.comeon.gamereviewservice.exceptions.ResourceNotFoundException;
import com.comeon.gamereviewservice.exceptions.ValidationException;
import com.comeon.gamereviewservice.v1.dtos.PlayerRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.PlayerResponse;
import com.comeon.gamereviewservice.v1.mapper.PlayerMapper;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import com.comeon.gamereviewservice.v1.repository.PlayerRepository;
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
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public List<PlayerResponse> getAllPlayers(Integer pageNo, Integer pageSize) {

        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by("firstName").ascending());

        Slice<PlayerEntity> slicedResult = playerRepository.findBy(paging);
        if (slicedResult.hasContent()) {
            return slicedResult.getContent().stream().map(playerEntity -> playerMapper.playerEntityToPlayerDto(playerEntity)).toList();
        }
        return Collections.emptyList();
    }

    public PlayerResponse getPlayerById(Long playerId) {
        PlayerEntity playerEntity = playerRepository.findById(playerId).orElseThrow(() -> new ResourceNotFoundException("No player match was found by player id [" + playerId + "]"));
        return playerMapper.playerEntityToPlayerDto(playerEntity);
    }

    /**
     * Player email is unique, make sure it is not already exist.
     * If Player email is exist then throw exception with proper message
     *
     * @param requestPayload
     * @return
     */
    public PlayerResponse addNewPlayer(PlayerRequestPayload requestPayload) {
        if (playerRepository.existsByEmail(requestPayload.getEmailAddress()))
            throw new ValidationException("Player email [{" + requestPayload.getEmailAddress() + "}] is already in use");
        PlayerEntity playerEntity = playerMapper.playerRequestPayloadToPlayerEntity(requestPayload);
        PlayerEntity persistEntity = playerRepository.save(playerEntity);
        return playerMapper.playerEntityToPlayerDto(persistEntity);
    }
}
