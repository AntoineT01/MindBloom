package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.QuizStatisticsDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Authority.NORMAL;
import static com.tux.mindbloom.config.Constants.Api.QUIZ_STATISTICS;

/**
 * Endpoints pour la gestion des statistiques de quiz.
 */
@Tag(name = "QuizStatistics")
@RequestMapping(path = QUIZ_STATISTICS)
public interface QuizStatisticsController {

    /**
     * Renvoie la liste de toutes les statistiques de quiz.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne toutes les statistiques de quiz")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<QuizStatisticsDto> findAll();

    /**
     * Crée de nouvelles statistiques pour un quiz.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée de nouvelles statistiques de quiz")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    QuizStatisticsDto create(
            @Parameter(required = true, description = "Des statistiques de quiz valides")
            @Valid
            @RequestBody QuizStatisticsDto dto
    );

    /**
     * Met à jour des statistiques de quiz via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour des statistiques de quiz par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    QuizStatisticsDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Les informations à mettre à jour")
            @Valid
            @RequestBody QuizStatisticsDto dto
    );

    /**
     * Supprime (ou marque comme supprimées) des statistiques de quiz via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime des statistiques de quiz par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
