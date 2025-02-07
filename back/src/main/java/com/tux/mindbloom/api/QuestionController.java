package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.QuestionDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.QUESTION;
import static com.tux.mindbloom.config.Constants.Authority.NORMAL;

/**
 * Endpoints for managing questions
 */
@Tag(name = "Question")
@RequestMapping(path = QUESTION)
public interface QuestionController {

  /**
   * Returns all existing questions
   *
   * @return the list of questions
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Returns all existing questions")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<QuestionDto> findAll();

  /**
   * Creates a new question
   *
   * @param dto the question to create
   * @return the created question
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Creates a question")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(NORMAL)
  QuestionDto create(
    @Parameter(required = true, description = "A valid question to create, must be provided")
    @Valid
    @RequestBody QuestionDto dto
  );

  /**
   * Updates an existing question identified by its id
   *
   * @param id  the identifier of the question (must be greater than 0)
   * @param dto the updated question data
   * @return the updated question
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Updates a question with a specific id")
  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  QuestionDto updateById(
    @Parameter(required = true, example = "1", description = "The required id, must be greater than 0")
    @Min(1)
    @PathVariable Long id,

    @Parameter(required = true, description = "A valid question to update, must be provided")
    @Valid
    @RequestBody QuestionDto dto
  );

  /**
   * Deletes an existing question identified by its id
   *
   * @param id the identifier of the question to delete (must be greater than 0)
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Deletes a question with a specific id")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void deleteById(
    @Parameter(required = true, example = "1", description = "The required id, must be greater than 0")
    @Min(1)
    @PathVariable Long id
  );

  /**
   * Returns all questions associated with a given quiz
   */
  @Operation(summary = "Gets all questions associated with a given quiz")
  @GetMapping(path = "/quiz/{quizId}")
  @ResponseStatus(HttpStatus.OK)
  List<QuestionDto> findByQuizId(
    @Parameter(required = true, example = "1", description = "The identifier of the quiz")
    @Min(1)
    @PathVariable Long quizId
  );
}
