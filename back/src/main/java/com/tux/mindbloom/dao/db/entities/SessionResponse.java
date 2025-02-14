package com.tux.mindbloom.dao.db.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représente la réponse (ou soumission) d'un participant à une question
 * dans le cadre d'une session de quiz.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "session_responses")
public class SessionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * L'identifiant de la session à laquelle cette réponse se rapporte.
     * (Peut être un @ManyToOne si vous avez l'entité QuizSession.)
     */
    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    /**
     * L'identifiant du participant qui a soumis la réponse.
     * (Peut être un @ManyToOne si vous avez l'entité Participant.)
     */
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    /**
     * L'identifiant de la question concernée par cette réponse.
     */
    @Column(name = "question_id", nullable = false)
    private Long questionId;

    /**
     * L'identifiant de la réponse choisie (le cas échéant).
     * Nullable, donc peut être null si la réponse est libre.
     */
    @Column(name = "answer_id")
    private Long answerId;

    /**
     * Le texte de la réponse saisie, si c'est une question ouverte.
     * Nullable pour les questions à choix multiples.
     */
    @Lob
    @Column(name = "response_text")
    private String responseText;

    /**
     * Moment où la réponse a été soumise.
     */
    @Column(name = "submitted_at", nullable = false)
    private LocalDateTime submittedAt;

    /**
     * Indicateur pour dire si la réponse est correcte.
     * Ex: 1 = correct, 0 = incorrect (ou tout autre convention).
     */
    @Column(name = "is_correct", nullable = false)
    private Integer isCorrect;
}
