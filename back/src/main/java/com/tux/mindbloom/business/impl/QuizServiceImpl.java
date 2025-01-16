package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.QuizDto;
import com.tux.mindbloom.business.QuizService;
import com.tux.mindbloom.business.mappers.QuizMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.QuizRepository;
import com.tux.mindbloom.dao.db.entities.Quiz;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
