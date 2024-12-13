package com.tanguylegoff.templateapp.api.models;

import com.tanguylegoff.templateapp.config.validation.annotation.MustBeAbsentForCreation;
import com.tanguylegoff.templateapp.config.validation.annotation.MustBePresentForUpdate;
import com.tanguylegoff.templateapp.config.validation.annotation.MustBeTheSameAsInPathOrMyself;
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
 * A user account
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "A user account")
public class AccountDto {

  /**
   * Internal identifier for an account
   *
   * @implNote Additional validations :
   * <ul>
   *   <li>Must be present for update/patch requests</li>
   *   <li>Must be absent for creation requests</li>
   *   <li>Must be the same as in path params for update/patch requests</li>
   * </ul>
   */
  @Schema(nullable = true, example = "1", description = """
    Internal identifier for a valid account<br/>
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
   * An account's firstname
   */
  @Schema(example = "The", description = "An account's firstname")
  @Size(min = 1, max = 50)
  @Pattern(regexp = "[a-zA-Z '-]*")
  private String firstname;

  /**
   * An account's lastname
   */
  @Schema(example = "Admin", description = "An account's lastname")
  @Size(min = 1, max = 50)
  @Pattern(regexp = "[a-zA-Z '-]*")
  private String lastname;

  /**
   * The account's profile, that will in the end define what they can and can't do. Will always be returned.
   * During signup, can be undefined, will be set by default to normal profile.
   */
  @Schema(description = """
    The account's profile, that will in the end define what they can and can't do. Will always be returned.
    During signup, can be undefined, will be set by default to normal profile.
    """)
  private ProfileDto profile;

  /**
   * An account's mail address
   */
  @Schema(example = "whatever@example.com", description = "An account's mail address")
  @NotNull
  @Size(min = 1, max = 50)
  @Email
  private String mail;

  /**
   * The provider of the OAuth service (e.g., google, microsoft, facebook)
   */
  @Schema(example = "google", description = "The provider of the OAuth service (e.g., google, microsoft, facebook)")
  @Size(max = 50)
  private String oauthProvider;

  //TODO: Faille ?
  /**
   * The unique identifier for the account from the OAuth provider.
   */
  @Schema(example = "1234567890", description = "The unique identifier for the account from the OAuth provider.")
  @Size(max = 255)
  private String oauthId;

  /**
   * IETF Tag of the selected locale for mails (fr, en)
   */
  @Schema(example = "fr", description = "IETF Tag of the selected locale for mails (fr, en)")
  @NotNull
  @Size(min = 1, max = 10)
  private String locale;
}
