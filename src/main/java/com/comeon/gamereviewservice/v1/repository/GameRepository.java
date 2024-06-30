package com.comeon.gamereviewservice.v1.repository;

import com.comeon.gamereviewservice.v1.model.GameEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameEntity, Long> {

    Slice<GameEntity> findBy(Pageable pageable);

    Optional<GameEntity> findByTitle(String title);

    boolean existsByTitle(String title);
}
