package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représente une réponse (soumission) d'un participant dans le cadre d'une session de quiz.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Une réponse soumise par un participant pour une question donnée durant une session.")
public class SessionResponseDto {

    /**
     * Identifiant interne de la réponse.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne de la réponse")
    private Long id;

    /**
     * L'ID de la session concernée.
     */
    @Schema(description = "ID de la session associée", example = "101")
    @NotNull
    private Long sessionId;

    /**
     * L'ID du participant ayant soumis la réponse.
     */
    @Schema(description = "ID du participant", example = "202")
    @NotNull
    private Long participantId;

    /**
     * L'ID de la question concernée.
     */
    @Schema(description = "ID de la question", example = "5")
    @NotNull
    private Long questionId;

    /**
     * L'ID de la réponse sélectionnée (si c'est une question QCM).
     */
    @Schema(description = "ID de la réponse choisie (si QCM)", example = "10", nullable = true)
    private Long answerId;

    /**
     * Le texte de la réponse saisie (si question ouverte).
     */
    @Schema(description = "Texte de la réponse (si question ouverte)", example = "Paris", nullable = true)
    private String responseText;

    /**
     * Date/heure de soumission.
     */
    @Schema(description = "Date/heure où la réponse a été soumise", example = "2025-01-01T08:30:00")
    @NotNull
    private LocalDateTime submittedAt;

    /**
     * Indique si la réponse est correcte (1) ou non (0).
     */
    @Schema(description = "1 si la réponse est correcte, 0 sinon", example = "1")
    @NotNull
    private Integer isCorrect;
}
