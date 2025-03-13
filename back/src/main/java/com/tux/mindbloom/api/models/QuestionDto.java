package com.tux.mindbloom.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tux.mindbloom.config.validation.annotation.MustBeAbsentForCreation;
import com.tux.mindbloom.config.validation.annotation.MustBePresentForUpdate;
import com.tux.mindbloom.config.validation.annotation.MustBeTheSameAsInPathOrMyself;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * A quiz question
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A quiz question")
public class QuestionDto {

  /**
   * Internal identifier for the question.
   * <ul>
   *   <li>Must be present for update/patch requests</li>
   *   <li>Must be absent for creation requests</li>
   *   <li>Must be the same as in path params for update/patch requests</li>
   * </ul>
   */
  @Schema(nullable = true, example = "1", description = "Internal identifier for a valid question. Must be present for update/patch requests, absent for creation requests, and the same as in path parameters.")
  @MustBeAbsentForCreation
  @MustBePresentForUpdate
  @MustBeTheSameAsInPathOrMyself
  @Min(1)
  private Long id;

  /**
   * Identifier of the quiz to which this question belongs.
   */
  @Schema(example = "1", description = "Identifier of the quiz to which this question belongs")
  @NotNull
  @Min(1)
  @Column(name = "quiz_id")
  @JsonProperty("quizId")
  private Long quizId;

  /**
   * The content of the question.
   */
  @Schema(example = "What is the capital of France?", description = "The content of the question")
  @NotNull
  @Size(min = 1)
  private String content;

  /**
   * The type of the question (e.g., 'multiple_choice', 'true_false', 'open').
   */
  @Schema(example = "multiple_choice", description = "The type of the question (e.g., 'multiple_choice', 'true_false', 'open')")
  @NotNull
  private String type;

  /**
   * Points awarded for a correct answer.
   */
  @Schema(example = "10", description = "Points awarded for a correct answer")
  @NotNull
  @Min(0)
  private Integer points;

  /**
   * The order of the question in the quiz.
   */
  @Schema(example = "1", description = "The order of the question in the quiz")
  @NotNull
  @Min(1)
  private Integer questionOrder;

  /**
   * Indicates if the question is required.
   */
  @Schema(example = "true", description = "Indicates if the question is required")
  @NotNull
  private Boolean isRequired;

  /**
   * Indicates if the question est active.
   */
  @Schema(example = "true", description = "Indicates if the question is active")
  @NotNull
  private Boolean isActive;

  /**
   * The display time for the question in seconds.
   */
  @Schema(example = "30", description = "The display time for the question in seconds")
  @NotNull
  @Min(0)
  private Integer displayTime;

  /**
   * The minimum time allowed to answer the question (optionnel).
   */
  @Schema(example = "10", description = "The minimum time allowed to answer the question (optional)")
  private Integer timeMin;

  /**
   * The maximum time allowed to answer the question (optionnel).
   */
  @Schema(example = "60", description = "The maximum time allowed to answer the question (optional)")
  private Integer timeMax;

  /**
   * Timestamp when the question was créée.
   */
  @Schema(example = "2025-01-01T12:00:00", description = "Timestamp when the question was created")
  private LocalDateTime createdAt;

  /**
   * Timestamp when the question a été mise à jour.
   */
  @Schema(example = "2025-01-02T15:30:00", description = "Timestamp when the question was last updated")
  private LocalDateTime updatedAt;

  /**
   * Indique si la question a été importée.
   */
  @Schema(example = "false", description = "Indicates if the question was imported")
  @NotNull
  private Boolean imported;
}
