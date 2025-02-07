package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.QuestionDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Contrôles pour les questions
 */
@Transactional
@Validated
public interface QuestionService {

  /**
   * Retourne toutes les questions existantes.
   *
   * @return la liste de toutes les questions
   */
  List<QuestionDto> findAll();

  /**
   * Retourne les questions correspondantes à un quiz id spécifique
   *
   * @param id l'identifiant requis du quiz, doit être renseigné et existant
   * @return Les questions trouvées
   * @throws EntityNotFoundException si les quiz n'ont pas été trouvés
   */
  List<QuestionDto> findByQuizId(@NotNull Long id);

  /**
   * Crée une nouvelle question.
   *
   * @param dto une question valide à créer, doit être renseignée
   * @return la question créée
   */
  QuestionDto create(@Valid @NotNull QuestionDto dto);

  /**
   * Met à jour une question avec un identifiant spécifique.
   *
   * @param id  l'identifiant requis, doit être renseigné
   * @param dto une question valide à mettre à jour, doit être renseignée
   * @return la question mise à jour
   * @throws EntityNotFoundException si la question n'a pas été trouvée
   */
  QuestionDto updateById(@NotNull Long id, @Valid @NotNull QuestionDto dto);

  /**
   * Supprime une question avec un identifiant spécifique.
   * Les questions ne sont pas réellement supprimées, elles peuvent être conservées pour des raisons d'historique.
   *
   * @param id l'identifiant requis, doit être renseigné
   * @throws EntityNotFoundException si la question n'a pas été trouvée
   */
  void deleteById(@NotNull Long id);
}
