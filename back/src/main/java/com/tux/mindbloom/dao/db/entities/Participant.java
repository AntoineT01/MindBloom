package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représentation d'un participant à une session.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "participants")
public class Participant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'identifiant de la session à laquelle ce participant est lié.
     * (Si vous avez une relation directe @ManyToOne, vous pouvez l'ajuster en conséquence.)
     */
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    /**
     * L'identifiant du compte (Account) correspondant.
     * (Selon vos besoins, peut être une vraie relation @ManyToOne.)
     */
    @Column(name = "account_id", nullable = false)
    private Long accountId;

    /**
     * Le pseudo (surnom) du participant pour cette session.
     */
    @Column(name = "nickname", nullable = false, length = 255)
    private String nickname;

    /**
     * Timestamp d'entrée dans la session.
     */
    @Column(name = "joined_at", nullable = false)
    private LocalDateTime joinedAt;
}
