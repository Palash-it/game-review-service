package com.comeon.gamereviewservice.v1.mapper;

import com.comeon.gamereviewservice.v1.dtos.GameRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.GameResponse;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface GameMapper {

    @Mapping(target = "id", source = "gameEntity.gameId")
    @Mapping(target = "gameTitle", source = "gameEntity.title")
    @Mapping(target = "gameDescription", source = "gameEntity.description")
    GameResponse gameEntityToGameDto(GameEntity gameEntity);


    @Mapping(target = "title", source = "gameRequestPayload.gameTitle")
    @Mapping(target = "description", source = "gameRequestPayload.gameDescription")
    GameEntity gameRequestPayloadToGameEntity(GameRequestPayload gameRequestPayload);
}
