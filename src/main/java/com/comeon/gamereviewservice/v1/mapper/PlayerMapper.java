package com.comeon.gamereviewservice.v1.mapper;

import com.comeon.gamereviewservice.v1.dtos.PlayerRequestPayload;
import com.comeon.gamereviewservice.v1.dtos.PlayerResponse;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PlayerMapper {

    @Mapping(target = "id", source = "playerEntity.playerId")
    @Mapping(target = "fname", source = "playerEntity.firstName")
    @Mapping(target = "lname", source = "playerEntity.lastName")
    @Mapping(target = "emailAddress", source = "playerEntity.email")
    PlayerResponse playerEntityToPlayerDto(PlayerEntity playerEntity);


    @Mapping(target = "firstName", source = "fname")
    @Mapping(target = "lastName", source = "lname")
    @Mapping(target = "email", source = "emailAddress")
    PlayerEntity playerRequestPayloadToPlayerEntity(PlayerRequestPayload playerRequestPayload);
}
