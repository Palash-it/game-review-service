package com.comeon.gamereviewservice.v1.model;

import com.comeon.gamereviewservice.enums.EntityStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "players", uniqueConstraints = @UniqueConstraint(columnNames = {"email"}))
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

    @PrePersist
    void prePersist(){
        this.setStatus(EntityStatus.ACTIVE);
    }
}
