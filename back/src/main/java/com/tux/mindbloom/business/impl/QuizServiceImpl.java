package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.QuizDto;
import com.tux.mindbloom.business.QuizService;
import com.tux.mindbloom.business.mappers.QuizMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.QuizRepository;
import com.tux.mindbloom.dao.db.entities.Quiz;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * implementation of {@link com.tux.mindbloom.business.QuizService}
 */
@Service
public class QuizServiceImpl implements QuizService {

  /**
   * DB Access for profiles
   */
  private final QuizRepository repository;

  /**
   * Profile / ProfileDto mapping
   */
  private final QuizMapper mapper;

  /**
   * Injection constructor
   *
   * @param repository DB Access for profiles
   * @param mapper     Profile / ProfileDto mapping
   */
  public QuizServiceImpl(QuizRepository repository, QuizMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QuizDto> findAll() {
    return mapper.toDtos(repository.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QuizDto findById(Long id) {
    return repository.findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException(Quiz.class.getSimpleName(), id));
  }

  @Override
  public QuizDto create(QuizDto dto) {
    // Transformation du DTO en entité
    Quiz quiz = mapper.toEntity(dto);

    // Par exemple, on initialise le statut du quiz, la date de création et de mise à jour
    quiz.setStatus(Quiz.Status.INACTIVE); // ou QuizStatus.ACTIVE si vous utilisez une énumération


    // Sauvegarde du quiz en base de données
    Quiz createdQuiz = repository.save(quiz);

    // Transformation de l'entité enregistrée en DTO

      return mapper.toDto(createdQuiz);
  }

  @Override
  public QuizDto updateById(Long id, QuizDto dto) {
    // Récupération du quiz existant par son id
    Optional<Quiz> optionalQuiz = repository.findById(id);

    if (!optionalQuiz.isPresent()) {
      // Gérer le cas où le quiz n'existe pas
      // Par exemple, lancer une exception ou renvoyer un résultat spécifique
      throw new EntityNotFoundException("Quiz with id " + id + " not found", id);
    }

    Quiz existingQuiz = optionalQuiz.get();

    existingQuiz.setTitle(dto.getTitle());
    existingQuiz.setDescription(dto.getDescription());
    existingQuiz.setIsPublic(dto.getIsPublic());
    existingQuiz.setShowAnswers(dto.getShowAnswers());
    existingQuiz.setShowFinalScore(dto.getShowFinalScore());
    existingQuiz.setTimeLimit(dto.getTimeLimit());

    // Si vous souhaitez modifier le statut à chaque mise à jour, par exemple :
    existingQuiz.setStatus(Quiz.Status.INACTIVE); // ou toute autre logique métier

    // Mise à jour de la date de modification
    existingQuiz.setUpdatedAt(LocalDateTime.now());

    // Vous pouvez également mettre à jour d'autres champs si nécessaire
    // Par exemple, si le share_code ne doit pas changer, il ne sera pas modifié ici.

    // Sauvegarde du quiz mis à jour en base de données
    Quiz updatedQuiz = repository.save(existingQuiz);

    // Transformation de l'entité mise à jour en DTO
    return mapper.toDto(updatedQuiz);
  }

  @Override
  public Object deleteById(Long id) {
    // Récupération du quiz existant par son id
    Optional<Quiz> optionalQuiz = repository.findById(id);

    if (!optionalQuiz.isPresent()) {
      // Gérer le cas où le quiz n'existe pas
      throw new EntityNotFoundException("Quiz with id " + id + " not found", id);
    }

    Quiz existingQuiz = optionalQuiz.get();

    // Mise à jour du statut pour marquer le quiz comme supprimé
    existingQuiz.setStatus(Quiz.Status.DELETED);
    // Mise à jour du timestamp de modification
    existingQuiz.setUpdatedAt(LocalDateTime.now());

    // Sauvegarde du quiz mis à jour en base de données
    repository.save(existingQuiz);

    return true;
  }



}
