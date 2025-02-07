package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.AnswerDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Transactional
@Validated
public interface AnswerService {

  List<AnswerDto> findAll();

  AnswerDto findById(@NotNull Long id);

  /**
   * Retrieves all answers for a specific question id.
   *
   * @param questionId the id of the question
   * @return list of answers for the given question
   */
  List<AnswerDto> findByQuestionId(@NotNull Long questionId);

  AnswerDto create(@Valid @NotNull AnswerDto dto);

  AnswerDto updateById(@NotNull Long id, @Valid @NotNull AnswerDto dto);

  void deleteById(@NotNull Long id);
}
