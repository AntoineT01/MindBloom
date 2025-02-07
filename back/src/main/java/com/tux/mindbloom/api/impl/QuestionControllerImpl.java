package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.QuestionController;
import com.tux.mindbloom.api.models.QuestionDto;
import com.tux.mindbloom.business.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.QUESTION;

/**
 * Implementation of {@link QuestionController}
 */
@Slf4j
@RestController
public class QuestionControllerImpl extends AbstractRestController implements QuestionController {

  /**
   * Controls for questions
   */
  private final QuestionService questionService;

  /**
   * Injection constructor
   *
   * @param questionService Controls for questions
   */
  public QuestionControllerImpl(QuestionService questionService) {
    this.questionService = questionService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QuestionDto> findAll() {
    log.info("Rest : GET - {}", QUESTION);
    return logResponse(log, "GET", questionService.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QuestionDto create(QuestionDto dto) {
    log.info("Rest : POST - {}", QUESTION);
    logRequest(log, dto);
    return logResponse(log, "POST", questionService.create(dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public QuestionDto updateById(Long id, QuestionDto dto) {
    log.info("Rest : PUT - {}/{}", QUESTION, id);
    logRequest(log, dto);
    return logResponse(log, "PUT", questionService.updateById(dto.getId(), dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    log.info("Rest : DELETE - {}/{}", QUESTION, id);
    questionService.deleteById(id);
    logResponse(log, "DELETE");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QuestionDto> findByQuizId(Long quizId) {
    return logResponse(log, "GET", questionService.findByQuizId(quizId));
  }
}
