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
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
@Audited
@EntityListeners(AuditingEntityListener.class)
@RequiredArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PlayerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "player_id")
    private Long playerId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;
    @Enumerated(EnumType.STRING)
    private EntityStatus status;

    @OneToMany(mappedBy = "player", cascade = CascadeType.REMOVE)
    private Set<PlayerGameInteractionEntity> playerGameActions;

    @PrePersist
    void prePersist(){
        this.setStatus(EntityStatus.ACTIVE);
    }
}
