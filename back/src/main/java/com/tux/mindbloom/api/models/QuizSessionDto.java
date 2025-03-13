package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représentation d'une session de quiz pour l'échange de données via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente une session de quiz.")
public class QuizSessionDto {

    /**
     * Identifiant interne de la session.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne de la session de quiz")
    private Long id;

    /**
     * L'ID du quiz auquel appartient cette session.
     */
    @Schema(description = "ID du quiz associé", example = "42")
    @NotNull
    private Long quizId;

    /**
     * Le mode de la session (ex: training, exam...).
     */
    @Schema(description = "Mode de la session", example = "training")
    @NotNull
    private String sessionMode;

    /**
     * Le statut de la session (ex: active, finished...).
     */
    @Schema(description = "Statut de la session", example = "active")
    @NotNull
    private String status;

    /**
     * Date/heure de début de la session.
     */
    @Schema(description = "Timestamp de début de la session", example = "2025-01-01T10:00:00")
    private LocalDateTime startTime;

    /**
     * Date/heure de fin de la session.
     */
    @Schema(description = "Timestamp de fin de la session", example = "2025-01-01T11:00:00")
    private LocalDateTime endTime;

    /**
     * Un code unique pour identifier la session.
     */
    @Schema(description = "Code unique de la session", example = "ABC123XYZ")
    @NotNull
    private String sessionCode;
}
