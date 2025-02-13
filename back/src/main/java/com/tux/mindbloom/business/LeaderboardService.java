package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.LeaderboardDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion du leaderboard.
 */
@Transactional
public interface LeaderboardService {

    /**
     * Récupère la liste de tous les scores (leaderboard) enregistrés.
     */
    List<LeaderboardDto> findAll();

    /**
     * Récupère un enregistrement de leaderboard par son identifiant.
     */
    LeaderboardDto findById(@NotNull Long id);

    /**
     * Crée un nouvel enregistrement dans le leaderboard.
     */
    LeaderboardDto create(LeaderboardDto dto);

    /**
     * Met à jour un enregistrement existant dans le leaderboard.
     */
    LeaderboardDto updateById(Long id, LeaderboardDto dto);

    /**
     * Supprime (ou marque comme supprimé) un enregistrement du leaderboard par son id.
     */
    Object deleteById(Long id);
}
