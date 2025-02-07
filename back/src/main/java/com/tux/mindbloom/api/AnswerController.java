package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.AnswerDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.ANSWER;
import static com.tux.mindbloom.config.Constants.Authority.NORMAL;

/**
 * Endpoints for managing answers.
 */
@Tag(name = "Answer")
@RequestMapping(path = ANSWER)
public interface AnswerController {

  /**
   * Returns all existing answers.
   *
   * @return list of all answers
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Returns all existing answers")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<AnswerDto> findAll();

  /**
   * Returns an answer by its id.
   *
   * @param id the id of the answer (must be > 0)
   * @return the answer
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Returns an answer by id")
  @GetMapping("/{id}")
  @ResponseStatus(HttpStatus.OK)
  AnswerDto findById(
    @Parameter(required = true, example = "1", description = "The id of the answer (must be > 0)")
    @Min(1) @PathVariable Long id);

  /**
   * Creates a new answer.
   *
   * @param dto the answer to create
   * @return the created answer
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Creates an answer")
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(NORMAL)
  AnswerDto create(
    @Parameter(required = true, description = "A valid answer to create, must be provided")
    @Valid @RequestBody AnswerDto dto);

  /**
   * Updates an existing answer by its id.
   *
   * @param id  the id of the answer (must be > 0)
   * @param dto the updated answer data
   * @return the updated answer
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Updates an answer with a specific id")
  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  AnswerDto updateById(
    @Parameter(required = true, example = "1", description = "The required id, must be greater than 0")
    @Min(1) @PathVariable Long id,
    @Parameter(required = true, description = "A valid answer to update, must be provided")
    @Valid @RequestBody AnswerDto dto);

  /**
   * Deletes an existing answer by its id.
   *
   * @param id the id of the answer to delete (must be > 0)
   */
  @Operation(security = { @SecurityRequirement(name = "bearer-key") }, summary = "Deletes an answer with a specific id")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void deleteById(
    @Parameter(required = true, example = "1", description = "The required id, must be greater than 0")
    @Min(1) @PathVariable Long id);

  /**
   * Returns all answers associated with a given question.
   *
   * @param questionId the id of the question
   * @return list of answers for the specified question
   */
  @Operation(summary = "Returns all answers for a given question id")
  @GetMapping(path = "/question/{questionId}")
  @ResponseStatus(HttpStatus.OK)
  List<AnswerDto> findByQuestionId(
    @Parameter(required = true, example = "1", description = "The id of the question")
    @Min(1) @PathVariable Long questionId);
}
