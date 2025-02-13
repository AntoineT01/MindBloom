package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.ParticipantDto;
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
// On suppose que vous avez une constante dans Constants.Api, par exemple:
import static com.tux.mindbloom.config.Constants.Api.PARTICIPANTS;

/**
 * Endpoints pour la gestion des participants.
 */
@Tag(name = "Participants")
@RequestMapping(path = PARTICIPANTS)
public interface ParticipantController {

    /**
     * Renvoie la liste de tous les participants existants.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne tous les participants")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<ParticipantDto> findAll();

    /**
     * Crée un nouveau participant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée un participant")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    ParticipantDto create(
            @Parameter(required = true, description = "Un DTO valide de participant")
            @Valid
            @RequestBody ParticipantDto dto
    );

    /**
     * Met à jour un participant existant via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour un participant par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    ParticipantDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Le DTO du participant à mettre à jour")
            @Valid
            @RequestBody ParticipantDto dto
    );

    /**
     * Supprime (ou marque comme supprimé) un participant existant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime un participant par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
