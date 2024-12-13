package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Used as a way to populate the FakeLoginController, to access the /login spring security filter
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Spring security's form login model")
public class FormLoginDto {
  /**
   * The username
   */
  @NotNull
  @Schema(description = "A valid account's email", example = "admin@example.com")
  private String email;

  /**
   * The password
   */
  @NotNull
  @Schema(description = "A valid account's encrypted password", example = "c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec")
  private String password;
}
