package com.comeon.gamereviewservice.v1.repository;

import com.comeon.gamereviewservice.v1.dtos.MostLovedGameResponse;
import com.comeon.gamereviewservice.v1.model.GameEntity;
import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import com.comeon.gamereviewservice.v1.model.PlayerGameInteractionEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerGameInteractionRepository extends JpaRepository<PlayerGameInteractionEntity, Long> {

    boolean existsByPlayerAndGameAndIsLovedTrue(PlayerEntity playerEntity, GameEntity gameEntity);

    boolean existsByPlayerAndGameAndIsLovedFalse(PlayerEntity playerEntity, GameEntity gameEntity);

    Optional<PlayerGameInteractionEntity> findByPlayerAndGameAndIsLovedFalse(PlayerEntity playerEntity, GameEntity gameEntity);

    Optional<PlayerGameInteractionEntity> findByPlayerAndGameAndIsLovedTrue(PlayerEntity playerEntity, GameEntity gameEntity);

    List<PlayerGameInteractionEntity> findByPlayerAndIsLovedTrue(PlayerEntity playerEntity);

    @Query(value = "SELECT pgi.game.gameId, count(pgi.interactionId) as loveCount FROM PlayerGameInteractionEntity AS pgi WHERE pgi.isLoved IS TRUE GROUP BY pgi.game.gameId ORDER BY loveCount DESC")
    List<Object[]> getMostLovedGames(Pageable pageable);
}
