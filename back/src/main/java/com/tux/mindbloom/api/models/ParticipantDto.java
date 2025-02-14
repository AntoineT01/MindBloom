package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Représentation d'un participant pour l'échange de données via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente un participant à une session.")
public class ParticipantDto {

    /**
     * Identifiant interne du participant.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne du participant")
    private Long id;

    /**
     * L'ID de la session associée.
     */
    @Schema(description = "ID de la session associée", example = "100")
    @NotNull
    private Long sessionId;

    /**
     * L'ID du compte associé.
     */
    @Schema(description = "ID du compte associé", example = "50")
    @NotNull
    private Long accountId;

    /**
     * Le pseudo (surnom) du participant pour cette session.
     */
    @Schema(description = "Pseudo du participant dans la session", example = "JohnDoe")
    @NotNull
    private String nickname;

    /**
     * Timestamp d'entrée dans la session.
     */
    @Schema(description = "Date/heure à laquelle le participant a rejoint la session", example = "2025-01-01T08:30:00")
    @NotNull
    private LocalDateTime joinedAt;
}
