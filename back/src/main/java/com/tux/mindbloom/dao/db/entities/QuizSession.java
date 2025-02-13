package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représentation d'une session de quiz.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "quiz_session")
public class QuizSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'identifiant du quiz associé à la session.
     */
    @Column(name = "quiz_id", nullable = false)
    private Long quizId;

    /**
     * Le mode de la session (ex: "training", "exam", etc.).
     */
    @Column(name = "session_mode", nullable = false, length = 255)
    private String sessionMode;

    /**
     * Le statut de la session (ex: "active", "finished", etc.).
     */
    @Column(name = "status", nullable = false, length = 255)
    private String status;

    /**
     * Le timestamp de début de la session.
     */
    @Column(name = "start_time")
    private LocalDateTime startTime;

    /**
     * Le timestamp de fin de la session.
     */
    @Column(name = "end_time")
    private LocalDateTime endTime;

    /**
     * Un code unique pour identifier la session.
     */
    @Column(name = "session_code", unique = true, nullable = false, length = 255)
    private String sessionCode;
}
