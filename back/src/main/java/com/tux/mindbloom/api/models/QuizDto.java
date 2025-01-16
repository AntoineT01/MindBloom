package com.tux.mindbloom.api.models;

import com.tux.mindbloom.config.validation.annotation.MustBeAbsentForCreation;
import com.tux.mindbloom.config.validation.annotation.MustBePresentForUpdate;
import com.tux.mindbloom.config.validation.annotation.MustBeTheSameAsInPathOrMyself;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
   *
   * @implNote Additional validations :
   * <ul>
   *   <li>Must be present for update/patch requests</li>
   *   <li>Must be absent for creation requests</li>
   *   <li>Must be the same as in path params for update/patch requests</li>
   * </ul>
   */
  @Schema(nullable = true, example = "1", description = """
    Internal identifier for a valid quiz<br/>
    Additional validations :
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
   * A quiz title
   */
  @Schema(example = "Cours mathématiques 2nd", description = "Le titre d'un quiz")
  @Size(min = 1, max = 255)
  @Pattern(regexp = "[a-zA-Z '-]*")
  private String title;

  @Schema(example = "Le quiz pose des questions de maths...", description = "La description d'un quiz")
  @Size(min = 1)
  @Pattern(regexp = "[a-zA-Z '-]*")
  private String description;

  @Schema(description = """
    The account that created the quiz
    """)
  private AccountDto accountCreator;

  @Schema(example = "true", description = "The quiz is public or not")
  @NotNull
  private Boolean isPublic;

  @Schema(example = "true", description = "The quiz shows answers or not")
  @NotNull
  private Boolean showAnswers;

  @Schema(example = "true", description = "The quiz shows final score or not")
  @NotNull
  private Boolean showFinalScore;

  @Schema(example = "30", description = """
  The maximum amount of time in **seconds** to complete each questions
  """)
  @NotNull
  private Integer timeLimit;


}
