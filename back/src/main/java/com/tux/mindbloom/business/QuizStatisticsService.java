package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.QuizStatisticsDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion des statistiques de quiz.
 */
@Transactional
public interface QuizStatisticsService {

    /**
     * Récupère la liste de toutes les statistiques de quiz.
     */
    List<QuizStatisticsDto> findAll();

    /**
     * Récupère des statistiques par leur identifiant.
     */
    QuizStatisticsDto findById(@NotNull Long id);

    /**
     * Crée de nouvelles statistiques de quiz.
     */
    QuizStatisticsDto create(QuizStatisticsDto dto);

    /**
     * Met à jour des statistiques de quiz existantes.
     */
    QuizStatisticsDto updateById(Long id, QuizStatisticsDto dto);

    /**
     * Supprime (ou marque comme supprimées) des statistiques par leur id.
     */
    Object deleteById(Long id);
}
