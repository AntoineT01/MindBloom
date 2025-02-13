package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.QuizDto;
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

import static com.tux.mindbloom.config.Constants.Api.QUIZ;
import static com.tux.mindbloom.config.Constants.Authority.NORMAL;

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

  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Creates a quiz
          """)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(NORMAL)
  QuizDto create(
          @Parameter(required = true, description = "a valid quiz to create, must be set")
          @Valid
          @RequestBody QuizDto dto
  );

  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Updates a Quiz with a specific id
          """)
  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  QuizDto updateById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id,

          @Parameter(required = true, description = "a valid account to update, must be set")
          @Valid
          @RequestBody QuizDto dto
  );
    @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Deletes a Quiz with a specific id.
          Quiz are not really deleted, we will keep them linked to past reviews
          """)
    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize(NORMAL)
    Object deleteById(
            @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
            @Min(1)
            @PathVariable Long id
    );
}
