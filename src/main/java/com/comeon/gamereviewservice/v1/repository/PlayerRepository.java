package com.comeon.gamereviewservice.v1.repository;

import com.comeon.gamereviewservice.v1.model.PlayerEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerEntity, Long> {

    Slice<PlayerEntity> findBy(Pageable pageable);

    boolean existsByEmail(String email);
}
