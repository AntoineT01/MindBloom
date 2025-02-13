package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

/**
 * Représentation d'un leaderboard lié à une session de quiz.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "leaderboard")
public class Leaderboard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'identifiant de la session à laquelle ce leaderboard se rapporte.
     */
    @Column(name = "quiz_session_id", nullable = false)
    private Long quizSessionId;

    /**
     * L'identifiant du participant figurant dans ce leaderboard.
     */
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    /**
     * Le score obtenu par ce participant.
     */
    @Column(name = "score", nullable = false)
    private Integer score;
}
