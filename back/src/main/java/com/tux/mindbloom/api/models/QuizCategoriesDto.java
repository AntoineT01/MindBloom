package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * DTO pour la liaison entre un quiz et une catégorie.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente l'association entre un quiz et une catégorie.")
public class QuizCategoriesDto {

    /**
     * ID du quiz.
     */
    @Schema(description = "L'ID du quiz")
    @NotNull
    private Long quizId;

    /**
     * ID de la catégorie.
     */
    @Schema(description = "L'ID de la catégorie")
    @NotNull
    private Long categoryId;
}
