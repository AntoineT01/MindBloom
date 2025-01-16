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
}
