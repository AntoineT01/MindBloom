package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.CategorieDto;
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
// On suppose une constante CATEGORIES = "/categories" dans Constants.Api
import static com.tux.mindbloom.config.Constants.Api.CATEGORIES;

/**
 * Endpoints pour la gestion des catégories.
 */
@Tag(name = "Categories")
@RequestMapping(path = CATEGORIES)
public interface CategorieController {

    /**
     * Renvoie la liste de toutes les catégories.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne toutes les catégories")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<CategorieDto> findAll();

    /**
     * Crée une nouvelle catégorie.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée une catégorie")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    CategorieDto create(
            @Parameter(required = true, description = "Un DTO valide de catégorie")
            @Valid
            @RequestBody CategorieDto dto
    );

    /**
     * Met à jour une catégorie via son identifiant.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour une catégorie par ID")
    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    CategorieDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id,

            @Parameter(required = true, description = "Le DTO de la catégorie à mettre à jour")
            @Valid
            @RequestBody CategorieDto dto
    );

    /**
     * Supprime (ou marque comme supprimée) une catégorie existante.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime une catégorie par ID")
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID requis, doit être > 0")
            @Min(1)
            @PathVariable Long id
    );
}
