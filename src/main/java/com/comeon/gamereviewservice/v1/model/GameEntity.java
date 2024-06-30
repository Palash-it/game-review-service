package com.comeon.gamereviewservice.v1.model;

import com.comeon.gamereviewservice.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "games", uniqueConstraints = @UniqueConstraint(columnNames = {"title"}))
@Audited
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class GameEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "game_id")
    private Long gameId;
    @Column(unique = true)
    private String title;
    private String description;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToMany(mappedBy = "game", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private Set<PlayerGameInteractionEntity> playerGameActions;

    @PrePersist
    void prePersist() {
        this.setStatus(EntityStatus.ACTIVE);
    }
}
