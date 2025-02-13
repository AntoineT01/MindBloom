package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.QuizSessionDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Interface de service pour la gestion des sessions de quiz.
 */
@Transactional
public interface QuizSessionService {

    /**
     * Récupère la liste de toutes les sessions de quiz.
     *
     * @return la liste de QuizSessionDto
     */
    List<QuizSessionDto> findAll();

    /**
     * Récupère une session de quiz par son identifiant.
     *
     * @param id l'id de la session
     * @return le QuizSessionDto correspondant
     */
    QuizSessionDto findById(@NotNull Long id);

    /**
     * Crée une nouvelle session de quiz à partir d'un DTO.
     *
     * @param dto les informations de la session à créer
     * @return le QuizSessionDto créé
     */
    QuizSessionDto create(QuizSessionDto dto);

    /**
     * Met à jour une session de quiz existante par son identifiant.
     *
     * @param id  l'id de la session à mettre à jour
     * @param dto les informations à mettre à jour
     * @return le QuizSessionDto après la mise à jour
     */
    QuizSessionDto updateById(Long id, QuizSessionDto dto);

    /**
     * Supprime (ou marque comme supprimée) une session de quiz par son id.
     *
     * @param id l'id de la session
     * @return un objet représentant le résultat de la suppression (true ou un message)
     */
    Object deleteById(Long id);
}
