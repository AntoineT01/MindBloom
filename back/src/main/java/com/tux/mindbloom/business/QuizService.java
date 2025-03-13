package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.QuizDto;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Controls for services
 */
@Transactional
public interface QuizService {

  List<QuizDto> findAll();

  QuizDto findById(@NotNull Long id);

  QuizDto create(QuizDto dto);

  QuizDto updateById(Long id, QuizDto dto);

  Object deleteById(Long id);

  List<QuizDto> findByUserId(@NotNull Long userId);
}
