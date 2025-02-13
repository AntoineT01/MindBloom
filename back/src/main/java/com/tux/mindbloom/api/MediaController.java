package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.MediaDto;
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
// Vous pouvez définir dans vos Constants.Api un MEDIA = "/media" par exemple.
import static com.tux.mindbloom.config.Constants.Api.MEDIA;

/**
 * Endpoints pour la gestion des médias.
 */
@Tag(name = "Media")
@RequestMapping(path = MEDIA)
public interface MediaController {

    /**
     * Renvoie la liste de tous les médias existants.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne tous les médias")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<MediaDto> findAll();

    /**
     * Crée un nouveau média.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée un média")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    MediaDto create(
            @Parameter(required = true, description = "Un DTO valide de media")
            @Valid
            @RequestBody MediaDto dto
    );

    /**
     * Met à jour un média existant via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour un média par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    MediaDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Le DTO du media à mettre à jour")
            @Valid
            @RequestBody MediaDto dto
    );

    /**
     * Supprime (ou marque comme supprimé) un média existant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime un média par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
