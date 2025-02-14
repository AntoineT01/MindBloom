package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.QuizCategoriesDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la liaison Quiz-Catégorie.
 */
@Transactional
public interface QuizCategoriesService {

    /**
     * Récupère toutes les liaisons quiz-catégorie existantes.
     */
    List<QuizCategoriesDto> findAll();

    /**
     * Récupère une liaison par (quizId, categoryId).
     */
    QuizCategoriesDto findById(@NotNull Long quizId, @NotNull Long categoryId);

    /**
     * Crée une nouvelle liaison quiz-catégorie.
     */
    QuizCategoriesDto create(QuizCategoriesDto dto);

    /**
     * Met à jour une liaison existante (même quizId, categoryId).
     */
    QuizCategoriesDto updateById(Long quizId, Long categoryId, QuizCategoriesDto dto);

    /**
     * Supprime la liaison correspondante (quizId, categoryId).
     */
    Object deleteById(Long quizId, Long categoryId);
}
