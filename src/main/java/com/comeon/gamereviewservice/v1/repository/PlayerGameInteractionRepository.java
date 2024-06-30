package com.comeon.gamereviewservice.v1.repository;

import com.comeon.gamereviewservice.v1.model.GameEntity;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import com.comeon.gamereviewservice.v1.model.PlayerGameInteractionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerGameInteractionRepository extends JpaRepository<PlayerGameInteractionEntity, Long> {

    boolean existsByPlayerAndGameAndIsLovedTrue(PlayerEntity playerEntity, GameEntity gameEntity);

    boolean existsByPlayerAndGameAndIsLovedFalse(PlayerEntity playerEntity, GameEntity gameEntity);

    Optional<PlayerGameInteractionEntity> findByPlayerAndGameAndIsLovedFalse(PlayerEntity playerEntity, GameEntity gameEntity);
    Optional<PlayerGameInteractionEntity> findByPlayerAndGameAndIsLovedTrue(PlayerEntity playerEntity, GameEntity gameEntity);
}
