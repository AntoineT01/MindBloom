package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * A user account
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A resolution of an account request")
public class AccountRequestDto {

  /**
   * An account request token
   */
  @Schema(example = "51e99a58-4c61-4bb2-87aa-29b7c32c531e", description = "An account request token")
  @NotNull
  @Size(min = 1, max = 50)
  private String token;

  /**
   * An account's encrypted password, will be used for login credential
   */
  @Schema(example = "c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec", description = "An account's encrypted password, will be used for login credential")
  @Size(min = 1, max = 255)
  @Pattern(regexp = "[a-zA-Z0-9]*")
  private String password;
}
