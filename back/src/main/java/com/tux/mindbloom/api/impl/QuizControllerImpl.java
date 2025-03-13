package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.QuizController;
import com.tux.mindbloom.api.models.QuizDto;
import com.tux.mindbloom.business.QuizService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.QUIZ;

/**
 * implementation of {@link com.tux.mindbloom.api.QuizController}
 */
@Slf4j
@RestController
public class QuizControllerImpl extends AbstractRestController implements QuizController {

  /**
   * Controls for profiles
   */
  private final QuizService quizService;

  /**
   * Injection constructor
   *
   * @param quizService Controls for accounts
   */
  public QuizControllerImpl(QuizService quizService) {
    this.quizService = quizService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<QuizDto> findAll() {
    log.info("Rest : GET - {}", QUIZ);
    return logResponse(log, "GET", quizService.findAll());
  }

  @Override
  public QuizDto create(QuizDto dto) {
    log.info("Rest : POST - {}", QUIZ);
    return logResponse(log, "POST", quizService.create(dto));
  }

  @Override
  public QuizDto updateById(Long id, QuizDto dto) {
    log.info("Rest : PUT - {}", QUIZ);
    return logResponse(log, "PUT", quizService.updateById(id,dto));
  }

  @Override
  public Object deleteById(Long id) {
    log.info("Rest : DELETE - {}", QUIZ);
    return logResponse(log, "DELETE", quizService.deleteById(id));
  }

  @Override
  public List<QuizDto> getByUserId(Long id) {
    log.info("Rest: GET quizzes for user id: {}/user/{}", QUIZ, id);
    return logResponse(log, "GET", quizService.findByUserId(id));
  }
}
