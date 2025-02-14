package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.QuizCategoriesDto;
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
// Supposez que dans votre Constants.Api, vous définissiez QUIZ_CATEGORIES = "/quiz-categories".
import static com.tux.mindbloom.config.Constants.Api.QUIZ_CATEGORIES;

/**
 * Endpoints pour la gestion de la liaison Quiz-Catégorie.
 */
@Tag(name = "QuizCategories")
@RequestMapping(path = QUIZ_CATEGORIES)
public interface QuizCategoriesController {

    /**
     * Renvoie toutes les liaisons quiz-catégorie existantes.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne toutes les liaisons quiz-catégorie")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<QuizCategoriesDto> findAll();

    /**
     * Crée une nouvelle liaison.
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Crée une liaison quiz-catégorie")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize(NORMAL)
    QuizCategoriesDto create(
            @Parameter(required = true, description = "Une liaison valide (quizId, categoryId)")
            @Valid
            @RequestBody QuizCategoriesDto dto
    );

    /**
     * Retourne une liaison précise (quizId, categoryId).
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Retourne une liaison quiz-catégorie par ID composite")
    @GetMapping(path = "/{quizId}/{categoryId}")
    @ResponseStatus(HttpStatus.OK)
    QuizCategoriesDto findById(
            @Parameter(required = true, example = "1", description = "L'ID du quiz")
            @Min(1)
            @PathVariable Long quizId,

            @Parameter(required = true, example = "1", description = "L'ID de la catégorie")
            @Min(1)
            @PathVariable Long categoryId
    );

    /**
     * Met à jour une liaison existante (quizId, categoryId).
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Met à jour une liaison quiz-catégorie")
    @PutMapping(path = "/{quizId}/{categoryId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize(NORMAL)
    QuizCategoriesDto updateById(
            @Parameter(required = true, example = "1", description = "L'ID du quiz dans la liaison")
            @Min(1)
            @PathVariable Long quizId,

            @Parameter(required = true, example = "1", description = "L'ID de la catégorie dans la liaison")
            @Min(1)
            @PathVariable Long categoryId,

            @Parameter(required = true, description = "Les infos de liaison à mettre à jour")
            @Valid
            @RequestBody QuizCategoriesDto dto
    );

    /**
     * Supprime une liaison (quizId, categoryId).
     */
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Supprime une liaison quiz-catégorie par ID composite")
    @DeleteMapping(path = "/{quizId}/{categoryId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "L'ID du quiz dans la liaison")
            @Min(1)
            @PathVariable Long quizId,

            @Parameter(required = true, example = "1", description = "L'ID de la catégorie dans la liaison")
            @Min(1)
            @PathVariable Long categoryId
    );
}
