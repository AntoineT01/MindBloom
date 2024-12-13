package com.tanguylegoff.templateapp.api.models;

import com.tanguylegoff.templateapp.config.validation.annotation.MustBeAbsentForCreation;
import com.tanguylegoff.templateapp.config.validation.annotation.MustBePresentForUpdate;
import com.tanguylegoff.templateapp.config.validation.annotation.MustBeTheSameAsInPath;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Country DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "A country")
public class CountryDto {

  /**
   * Internal identifier for a country
   *
   * <ul>
   *   <li>Must be the same as in path params for update / patch requests</li>
   *   <li>Must be present for update / patch requests</li>
   *   <li>Must be absent for creation requests</li>
   * </ul>
   */
  @Schema(example = "1", description = """
           Internal identifier for a country<br/>
           Additional validation :
           <ul>
             <li>Must be the same as in path params for update / patch requests</li>
             <li>Must be present for update / patch requests</li>
             <li>Must be absent for creation requests</li>
           </ul>
          """)
  @MustBeAbsentForCreation
  @MustBePresentForUpdate
  @MustBeTheSameAsInPath
  @Min(1)
  private Long id;

  /**
   * Country's label. Must be at least 2 characters long, 56 characters max, and contain only letters
   */
  @Schema(example = "France", description = """
          Country's label. Must be at least 2 characters long, 56 characters max, and contain only letters.
          """)
  @Size(min = 2, max = 56) // 56 is the longest country name in the world, UK
  @Pattern(regexp = "[a-zA-Z]+")
  @Nullable
  private String label;
}
