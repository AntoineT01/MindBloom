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
 * A quiz
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A quiz")
public class QuizDto {

  /**
   * Internal identifier for a quiz
   */
  @Schema(nullable = true, example = "1", description = """
        Internal identifier for a valid quiz<br/>
        Additional validations:
        <ul>
            <li>Must be present for update/patch requests</li>
            <li>Must be absent for creation requests</li>
            <li>Must be the same as in path params for update/patch requests or path must be "/me"</li>
        </ul>
        """)
  @MustBeAbsentForCreation
  @MustBePresentForUpdate
  @MustBeTheSameAsInPathOrMyself
  @Min(1)
  private Long id;

  /**
   * The title of a quiz
   */
  @Schema(example = "Math Course 2nd Grade", description = "The title of a quiz")
  @Size(min = 1, max = 255)
  private String title;

  /**
   * The description of a quiz
   */
  @Schema(example = "The quiz includes various math questions...", description = "The description of a quiz")
  @Size(min = 1, max = 255)
  private String description;

  /**
   * The account that created the quiz
   */
  @Schema(description = "The account that created the quiz")
  @NotNull
  private AccountDto creator;

  /**
   * Whether the quiz is public or not
   */
  @Schema(example = "true", description = "Whether the quiz is public or not")
  @NotNull
  private Boolean isPublic;

  /**
   * Whether the quiz shows answers or not
   */
  @Schema(example = "true", description = "Whether the quiz shows answers or not")
  @NotNull
  private Boolean showAnswers;

  /**
   * Whether the quiz shows the final score or not
   */
  @Schema(example = "true", description = "Whether the quiz shows the final score or not")
  @NotNull
  private Boolean showFinalScore;

  /**
   * The maximum amount of time in **seconds** to complete each question
   */
  @Schema(example = "30", description = "The maximum amount of time in **seconds** to complete each question")
  @NotNull
  private Integer timeLimit;

  /**
   * The timestamp when the quiz was created
   */
  @Schema(example = "2023-01-01T12:00:00", description = "The timestamp when the quiz was created")
  private LocalDateTime createdAt;

  /**
   * The timestamp when the quiz was last updated
   */
  @Schema(example = "2023-01-02T12:00:00", description = "The timestamp when the quiz was last updated")
  private LocalDateTime updatedAt;

  /**
   * The status of the quiz
   */
  @Schema(description = "The status of the quiz", example = "active")
  @NotNull
  private Status status;

  /**
   * The unique share code for the quiz
   */
  @Schema(example = "ABC123", description = "The unique share code for the quiz")
  @NotNull
  @Size(max = 255)
  private String shareCode;

  /**
   * Enumeration representing the possible statuses of a quiz.
   */
  public enum Status {
    ACTIVE,
    INACTIVE,
    DELETED
  }
}
