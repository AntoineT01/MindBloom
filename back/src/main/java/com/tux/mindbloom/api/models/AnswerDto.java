package com.tux.mindbloom.api.models;

import com.tux.mindbloom.config.validation.annotation.MustBeAbsentForCreation;
import com.tux.mindbloom.config.validation.annotation.MustBePresentForUpdate;
import com.tux.mindbloom.config.validation.annotation.MustBeTheSameAsInPathOrMyself;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * A DTO representing an answer for a question.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "An answer for a question")
public class AnswerDto {

  @Schema(nullable = true, example = "1", description = "Internal identifier for the answer. Must be absent for creation and present for update.")
  @MustBeAbsentForCreation
  @MustBePresentForUpdate
  @MustBeTheSameAsInPathOrMyself
  @Min(1)
  private Long id;

  @Schema(example = "1", description = "Identifier of the question to which this answer belongs")
  @NotNull
  @Min(1)
  private Long questionId;

  @Schema(example = "Paris", description = "Content of the answer")
  @NotNull
  @Size(min = 1)
  private String content;

  @Schema(example = "multiple_choice", description = "The type of the answer")
  @NotNull
  private String type;

  @Schema(example = "true", description = "Indicates if this answer is correct")
  @NotNull
  private Boolean isCorrect;

  @Schema(example = "1", description = "Display order of the answer")
  @NotNull
  @Min(1)
  private Integer answerOrder;

  @Schema(example = "2025-01-01T12:00:00", description = "Timestamp when the answer was created")
  private LocalDateTime createdAt;

  @Schema(example = "2025-01-02T15:30:00", description = "Timestamp when the answer was last updated")
  private LocalDateTime updatedAt;
}
