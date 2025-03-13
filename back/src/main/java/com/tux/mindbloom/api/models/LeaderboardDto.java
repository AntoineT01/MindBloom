package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Représentation du leaderboard (score d'un participant dans une session)
 * pour l'échange de données via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente un score d'un participant dans une session (leaderboard).")
public class LeaderboardDto {

    /**
     * Identifiant interne du leaderboard.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne du leaderboard")
    private Long id;

    /**
     * L'ID de la session de quiz à laquelle ce leaderboard se rapporte.
     */
    @Schema(description = "ID de la session associée", example = "101")
    @NotNull
    private Long quizSessionId;

    /**
     * L'ID du participant associé.
     */
    @Schema(description = "ID du participant concerné", example = "202")
    @NotNull
    private Long participantId;

    /**
     * Le score obtenu par ce participant.
     */
    @Schema(description = "Score obtenu", example = "87")
    @NotNull
    private Integer score;
}
