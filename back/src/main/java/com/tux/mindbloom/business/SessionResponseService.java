package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.SessionResponseDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion des réponses de session (session_responses).
 */
@Transactional
public interface SessionResponseService {

    /**
     * Récupère la liste de toutes les réponses existantes.
     */
    List<SessionResponseDto> findAll();

    /**
     * Récupère une réponse par son identifiant.
     */
    SessionResponseDto findById(@NotNull Long id);

    /**
     * Crée une nouvelle réponse.
     */
    SessionResponseDto create(SessionResponseDto dto);

    /**
     * Met à jour une réponse existante par son identifiant.
     */
    SessionResponseDto updateById(Long id, SessionResponseDto dto);

    /**
     * Supprime (ou marque comme supprimée) une réponse par son id.
     */
    Object deleteById(Long id);
}
