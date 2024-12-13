package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.FormLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tux.mindbloom.config.Constants.Api.ACCOUNTS;
import static com.tux.mindbloom.config.Constants.Api.LOGOUT;
import static com.tux.mindbloom.config.Constants.Api.ME;
import static com.tux.mindbloom.config.Constants.Api.PASSWORD;
import static com.tux.mindbloom.config.Constants.Authority.NORMAL;


/**
 * Endpoints for the connected user's account
 */
@Tag(name = "My Account")
@RequestMapping(path = ACCOUNTS + ME)
@Validated
public interface MyAccountController {

  /**
   * Returns the account currently connected.
   * The account's password will never be returned
   *
   * @return the found account
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
    Returns the account currently connected.
    The account's password will never be returned.
    """)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  AccountDto getMyAccount();

  /**
   * Updates the account currently connected.
   * The account's password will never be returned.
   * Will prevent the profile from being updated.
   *
   * @param dto a valid account to update, must be set
   * @return the updated thing
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
    Updates the account currently connected.
    The account's password will never be returned.
    Will prevent the profile from being updated.
    """)
  @PutMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  AccountDto updateMyAccount(
    @Parameter(required = true, description = "a account to update")
    @Valid
    @RequestBody AccountDto dto
  );

  /**
   * Update the currently connected account password
   * The account password will never be returned
   */
  @PutMapping(path = PASSWORD, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void updateMyPassword(
    @Parameter(required = true, description = "the new password")
    @Valid
    @RequestBody FormLoginDto password
  );

  /**
   * Deletes the account currently connected.
   * Don't forget to clear session, jwt is still valid.
   * Accounts are not really deleted, we will keep them linked to past reviews
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
    Deletes the account currently connected.
    Don't forget to clear session.
    Accounts are not really deleted, we will keep them linked to past reviews.
    """)
  @DeleteMapping
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void deleteMyAccount();

  /**
   * Logs out the account currently connected.
   * This will invalidate the authentication token.
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
    Logs out the account currently connected.
    This will invalidate the authentication token.
    """)
  @PostMapping(LOGOUT)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void logout(HttpServletResponse response);
}
