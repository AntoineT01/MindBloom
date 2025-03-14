package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.QuestionDto;
import com.tux.mindbloom.business.QuestionService;
import com.tux.mindbloom.business.mappers.QuestionMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.QuestionRepository;
import com.tux.mindbloom.dao.db.QuizRepository;
import com.tux.mindbloom.dao.db.entities.Question;
import com.tux.mindbloom.dao.db.entities.Quiz;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implémentation de {@link QuestionService}
 */
@Slf4j
@Service
public class QuestionServiceImpl implements QuestionService {

  /**
   * Accès à la base de données pour les questions
   */
  private final QuestionRepository repository;

  /**
   * Mapping entre Question et QuestionDto
   */
  private final QuestionMapper mapper;
  private final QuizRepository quizRepository;

  /**
   * Constructeur d'injection
   *
   * @param repository DB Access for questions
   * @param mapper     Mapping entre Question et QuestionDto
   */
  public QuestionServiceImpl(QuestionRepository repository, QuestionMapper mapper, QuizRepository quizRepository) {
    this.repository = repository;
    this.quizRepository = quizRepository;
    this.mapper = mapper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QuestionDto> findAll() {
    log.info("Service: GET all questions");
    return mapper.toDtos(repository.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QuestionDto> findByQuizId(Long id) {
    log.info("Service: GET questions by quiz id: {}", id);
    List<Question> questions = repository.findByQuizId(id);
    if (questions == null || questions.isEmpty()) {
      throw new EntityNotFoundException(Question.class.getSimpleName(), id);
    }
    return questions.stream()
      .map(mapper::toDto)
      .collect(Collectors.toList());
  }
  /**
   * {@inheritDoc}
   */
  @Override
  public QuestionDto create(QuestionDto dto) {
      log.info("Service: POST create question");

      // Récupérer le Quiz correspondant à l'ID fourni dans le DTO
      Quiz quiz = quizRepository.findById(dto.getQuizId())
              .orElseThrow(() -> new EntityNotFoundException("Quiz", dto.getQuizId()));

      // Mapper le DTO vers l'entité Question
      Question question = mapper.toEntity(dto);

      // Associer l'objet Quiz récupéré à la Question
      question.setQuiz(quiz);

      // Définir la date de création si nécessaire
      if (question.getCreatedAt() == null) {
          question.setCreatedAt(LocalDateTime.now());
      }

      // Sauvegarder la question en base
      Question created = repository.save(question);
      return mapper.toDto(created);
  }


  /**
   * {@inheritDoc}
   */
  @Override
  public QuestionDto updateById(Long id, QuestionDto dto) {
    log.info("Service: PUT update question with id: {}", id);
    Question updated = repository.findById(id)
      .map(existing -> {
        // Mise à jour des champs modifiables
// Récupérer l'objet Quiz correspondant à l'id contenu dans le DTO
        Quiz quiz = quizRepository.findById(dto.getQuizId())
          .orElseThrow(() -> new EntityNotFoundException("Quiz", dto.getQuizId()));
// Assigner l'objet Quiz récupéré à l'entité Question
        existing.setQuiz(quiz);
        existing.setContent(dto.getContent());
        existing.setType(dto.getType());
        existing.setPoints(dto.getPoints());
        existing.setQuestionOrder(dto.getQuestionOrder());
        existing.setIsRequired(dto.getIsRequired());
        existing.setIsActive(dto.getIsActive());
        existing.setDisplayTime(dto.getDisplayTime());
        existing.setTimeMin(dto.getTimeMin());
        existing.setTimeMax(dto.getTimeMax());
        existing.setImported(dto.getImported());
        // On met à jour le timestamp de modification
        existing.setUpdatedAt(LocalDateTime.now());
        return existing;
      })
      .orElseThrow(() -> new EntityNotFoundException("Question", id));
    Question saved = repository.save(updated);
    return mapper.toDto(saved);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    log.info("Service: DELETE question with id: {}", id);
    Question question = repository.findById(id)
      .orElseThrow(() -> new EntityNotFoundException("Question", id));
    // Suppression logique : on désactive la question plutôt que de la supprimer physiquement
    question.setIsActive(false);
    repository.save(question);
  }
}
