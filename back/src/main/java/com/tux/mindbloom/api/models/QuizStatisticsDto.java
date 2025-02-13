package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

/**
 * Représentation des statistiques liées à un quiz pour l'échange de données via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente les statistiques liées à un quiz.")
public class QuizStatisticsDto {

    /**
     * Identifiant interne.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne des statistiques")
    private Long id;

    /**
     * L'ID du quiz auquel appartiennent ces statistiques.
     */
    @Schema(description = "ID du quiz associé", example = "42")
    @NotNull
    private Long quizId;

    /**
     * Le nombre total de participants.
     */
    @Schema(description = "Nombre total de participants au quiz", example = "100")
    @NotNull
    private Integer totalParticipants;

    /**
     * Le score moyen obtenu au quiz.
     */
    @Schema(description = "Score moyen (sur le quiz)", example = "75.25")
    private BigDecimal averageScore;

    /**
     * Le temps moyen par question.
     */
    @Schema(description = "Temps moyen par question (en secondes ?)", example = "12.5")
    private BigDecimal averageTimePerQuestion;
}
