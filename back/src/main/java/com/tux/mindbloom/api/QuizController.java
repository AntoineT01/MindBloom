package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.QuizDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.QUIZ;

/**
 * Endpoints for quiz
 */
@Tag(name = "Quiz")
@RequestMapping(path = QUIZ)
public interface QuizController {

  /**
   * Returns all existing quiz
   *
   * @return all existing quiz
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Returns all existing quiz")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<QuizDto> findAll();

}
