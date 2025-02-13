package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.QuizSessionDto;
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
import static com.tux.mindbloom.config.Constants.Api.QUIZ_SESSION;

/**
 * Endpoints pour la gestion des sessions de quiz.
 */
@Tag(name = "QuizSession")
@RequestMapping(path = QUIZ_SESSION)
public interface QuizSessionController {

    /**
     * Renvoie la liste de toutes les sessions de quiz existantes.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne toutes les sessions de quiz")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<QuizSessionDto> findAll();

    /**
     * Crée une nouvelle session de quiz.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée une session de quiz")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    QuizSessionDto create(
            @Parameter(required = true, description = "Une session de quiz valide")
            @Valid
            @RequestBody QuizSessionDto dto
    );

    /**
     * Met à jour une session de quiz existante via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour une session de quiz par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    QuizSessionDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Les informations à mettre à jour")
            @Valid
            @RequestBody QuizSessionDto dto
    );

    /**
     * Supprime (ou marque comme supprimée) une session de quiz existante.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime une session de quiz par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
