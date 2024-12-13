package com.tux.mindbloom.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A signup form, essentially
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A signup form, essentially")
public class SignupDto {

  /**
   * An account's firstname
   */
  @Schema(example = "Some", description = "An account's firstname")
  @Size(min = 1, max = 50)
  @Pattern(regexp = "[a-zA-Z '-]*")
  @Nullable
  private String firstname;

  /**
   * An account's lastname
   */
  @Schema(example = "Account", description = "An account's lastname")
  @Size(min = 1, max = 50)
  @Nullable
  @Pattern(regexp = "[a-zA-Z '-]*")
  private String lastname;

  /**
   * An account's mail address
   */
  @Schema(example = "whatever@example.com", description = "An account's mail address")
  @NotNull
  @Size(min = 1, max = 50)
  @Email
  private String mail;

  /**
   * IETF Tag of the selected locale for mails (fr, en)
   */
  @Schema(example = "fr", description = "IETF Tag of the selected locale for mails (fr, en)")
  @NotNull
  @Size(min = 1, max = 10)
  private String locale;

  /**
   * An account's encrypted password, will be used for login credential
   */
  @Schema(example = "c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec", description = "An account's encrypted password, will be used for login credential")
  @Size(min = 1, max = 255)
  @NotNull
  private String password;

  /**
   * The provider of the OAuth service (e.g., google, microsoft, facebook)
   */
  @Schema(example = "google", description = "The provider of the OAuth service (e.g., google, microsoft, facebook)")
  @Size(max = 50)
  @Nullable
  private String oauthProvider;

  /**
   * The unique identifier for the account from the OAuth provider.
   */
  @Schema(example = "1234567890", description = "The unique identifier for the account from the OAuth provider.")
  @Size(max = 255)
  @Nullable
  private String oauthId;
}
