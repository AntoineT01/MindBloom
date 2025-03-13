package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.*;

/**
 * Représentation d'une catégorie pour l'échange de données via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente une catégorie.")
public class CategorieDto {

    /**
     * Identifiant interne de la catégorie.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne de la catégorie")
    private Long id;

    /**
     * Le nom de la catégorie.
     */
    @Schema(description = "Nom de la catégorie", example = "Mathématiques")
    @NotNull
    private String name;

    /**
     * La description de la catégorie.
     */
    @Schema(description = "Description de la catégorie", example = "Regroupe tous les quiz sur les mathématiques")
    private String description;
}
