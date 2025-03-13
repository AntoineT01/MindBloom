package com.tux.mindbloom.api.models;

import com.tux.mindbloom.enums.MediaType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
/**
 * Représentation d'un média pour l'échange de données via l'API.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Représente un média lié à une question.")
public class MediaDto {

    /**
     * Identifiant interne du média.
     */
    @Schema(nullable = true, example = "1", description = "Identifiant interne du média")
    private Long id;

    /**
     * L'ID de la question à laquelle ce média est associé.
     */
    @Schema(description = "ID de la question associée", example = "42")
    @NotNull
    private Long questionId;

    /**
     * Le type du média : IMAGE, VIDEO ou AUDIO.
     */
    @Schema(description = "Type du média", example = "IMAGE")
    @NotNull
    private MediaType type;

    /**
     * L'URL où se trouve le média.
     */
    @Schema(description = "URL du média", example = "https://exemple.com/image.jpg")
    @NotEmpty
    private String url;

}
