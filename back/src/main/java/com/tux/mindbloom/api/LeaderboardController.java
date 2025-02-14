package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.LeaderboardDto;
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
// On suppose que vous avez une constante LEADERBOARD = "/leaderboard" dans Constants.Api
import static com.tux.mindbloom.config.Constants.Api.LEADERBOARD;

/**
 * Endpoints pour la gestion du leaderboard.
 */
@Tag(name = "Leaderboard")
@RequestMapping(path = LEADERBOARD)
public interface LeaderboardController {

    /**
     * Renvoie la liste de tous les enregistrements du leaderboard.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne tous les scores (leaderboard)")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<LeaderboardDto> findAll();

    /**
     * Crée un nouvel enregistrement dans le leaderboard.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée un nouvel enregistrement du leaderboard")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    LeaderboardDto create(
            @Parameter(required = true, description = "Un DTO valide pour l'enregistrement leaderboard")
            @Valid
            @RequestBody LeaderboardDto dto
    );

    /**
     * Met à jour un enregistrement existant dans le leaderboard.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour un enregistrement du leaderboard par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    LeaderboardDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Les informations à mettre à jour")
            @Valid
            @RequestBody LeaderboardDto dto
    );

    /**
     * Supprime (ou marque comme supprimé) un enregistrement du leaderboard.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime un enregistrement du leaderboard par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
