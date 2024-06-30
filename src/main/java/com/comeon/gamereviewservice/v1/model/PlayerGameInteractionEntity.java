package com.comeon.gamereviewservice.v1.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Entity
@Table(name = "player_game_interaction", uniqueConstraints = {@UniqueConstraint(columnNames = {"player_id", "game_id"})})
@Audited
@EntityListeners(AuditingEntityListener.class)

@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlayerGameInteractionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "interaction_id")
    private Long interactionId;

    @ManyToOne
    @JoinColumn(name = "player_id", nullable = false)
    private PlayerEntity player;

    @ManyToOne
    @JoinColumn(name = "game_id", nullable = false)
    private GameEntity game;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "last_modified_at")
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Column(name = "is_loved", nullable = false)
    private Boolean isLoved;

    @PreUpdate
    void preUpdate() {
        setLastModifiedAt(LocalDateTime.now());
    }

}
