package com.tux.mindbloom.api.models;

import com.tux.mindbloom.config.validation.annotation.MustBeAbsentForCreation;
import com.tux.mindbloom.config.validation.annotation.MustBePresentForUpdate;
import com.tux.mindbloom.config.validation.annotation.MustBeTheSameAsInPath;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A connected account's profile
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A connected account's profile")
public class ProfileDto {

  /**
   * Internal identifier for an account profile
   *
   * @implNote Additional validations :
   * <ul>
   *   <li>Must be present for creation requests</li>
   *   <li>Must be absent for update / patch requests</li>
   *   <li>Must be the same as in path params for update / patch requests</li>
   * </ul>
   */
  @Schema(nullable = true, example = "1", description = """
          Internal identifier for an account profile<br/>
          Additional validations :
          <ul>
              <li>Must be present for creation requests</li>
              <li>Must be absent for update / patch requests</li>
              <li>Must be the same as in path params for update / patch requests</li>
          </ul>
          """)
  @MustBeAbsentForCreation
  @MustBePresentForUpdate
  @MustBeTheSameAsInPath
  @Min(1)
  private Long id;

  /**
   * The profile's label
   */
  @NotNull
  @Schema(example = "Administrator", description = "The profile's label")
  private String label;
}
