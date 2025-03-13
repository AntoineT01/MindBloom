package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.ParticipantDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion des participants.
 */
@Transactional
public interface ParticipantService {

    /**
     * Récupère la liste de tous les participants existants.
     */
    List<ParticipantDto> findAll();

    /**
     * Récupère un participant par son identifiant.
     */
    ParticipantDto findById(@NotNull Long id);

    /**
     * Crée un nouveau participant.
     */
    ParticipantDto create(ParticipantDto dto);

    /**
     * Met à jour un participant existant par son identifiant.
     */
    ParticipantDto updateById(Long id, ParticipantDto dto);

    /**
     * Supprime (ou marque comme supprimé) un participant par son id.
     */
    Object deleteById(Long id);
}
