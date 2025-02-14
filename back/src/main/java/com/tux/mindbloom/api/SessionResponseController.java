package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.SessionResponseDto;
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
// On suppose une constante SESSION_RESPONSES = "/session-responses" dans Constants.Api
import static com.tux.mindbloom.config.Constants.Api.SESSION_RESPONSES;

/**
 * Endpoints pour la gestion des réponses de session (session_responses).
 */
@Tag(name = "SessionResponses")
@RequestMapping(path = SESSION_RESPONSES)
public interface SessionResponseController {

    /**
     * Renvoie la liste de toutes les réponses existantes.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne toutes les réponses")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<SessionResponseDto> findAll();

    /**
     * Crée une nouvelle réponse.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée une réponse")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    SessionResponseDto create(
            @Parameter(required = true, description = "Un DTO valide de session_response")
            @Valid
            @RequestBody SessionResponseDto dto
    );

    /**
     * Met à jour une réponse existante via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour une réponse par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    SessionResponseDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Le DTO de la session_response à mettre à jour")
            @Valid
            @RequestBody SessionResponseDto dto
    );

    /**
     * Supprime (ou marque comme supprimé) une réponse existante.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime une réponse par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
