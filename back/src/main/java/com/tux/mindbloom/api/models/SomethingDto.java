package com.tux.mindbloom.api.models;

import com.tux.mindbloom.config.validation.annotation.MustBeAbsentForCreation;
import com.tux.mindbloom.config.validation.annotation.MustBePresentForUpdate;
import com.tux.mindbloom.config.validation.annotation.MustBeTheSameAsInPath;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Random model
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Something for starters")
public class SomethingDto {

  /**
   * Internal identifier for this sample table
   *
   * @implNote Additional validations :
   * <ul>
   *   <li>Must be absent for creation requests</li>
   *   <li>Must be present for update / patch requests</li>
   *   <li>Must be the same as in path params for update / patch requests</li>
   * </ul>
   */
  @Schema(nullable = true, example = "1", description = """
          Internal identifier for this sample table<br/>
          Additional validations :
          <ul>
              <li>Must be absent for creation requests</li>
              <li>Must be present for update / patch requests</li>
              <li>Must be the same as in path params for update / patch requests</li>
          </ul>
          """)
  @MustBeAbsentForCreation
  @MustBePresentForUpdate
  @MustBeTheSameAsInPath
  @Min(1)
  private Long id;

  /**
   * Some random label.
   */
  @Schema(example = "Sample", description = "Some random label.")
  @NotNull
  @Size(min = 1, max = 20)
  @Pattern(regexp = "[a-zA-Z]*")
  private String label;
}
