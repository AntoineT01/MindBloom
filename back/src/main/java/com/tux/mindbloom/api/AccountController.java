package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.AccountDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.ACCOUNTS;
import static com.tux.mindbloom.config.Constants.Authority.ADMINISTRATOR;


/**
 * Endpoints for accounts
 */
@Tag(name = "All Accounts")
@RequestMapping(path = ACCOUNTS)
@Validated
public interface AccountController {

  /**
   * Returns all existing accounts
   * The account's password will never be returned.
   *
   * @return all existing accounts
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Returns all existing accounts
          The account's password will never be returned.
          """)
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(ADMINISTRATOR)
  List<AccountDto> findAll();

  /**
   * Returns an account with a specific id
   * The account's password will never be returned.
   *
   * @param id the required id, must be set and greater than 0
   * @return the found thing
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Returns an account with a specific id
          The account's password will never be returned.
          """)
  @GetMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(ADMINISTRATOR)
  AccountDto findById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id
  );

  /**
   * Creates an account
   * The account's password will never be returned.
   *
   * @param dto a valid account to create, must be set
   * @return the created account
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Creates an account
          The account's password will never be returned.
          """)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(ADMINISTRATOR)
  AccountDto create(
          @Parameter(required = true, description = "a valid account to create, must be set")
          @Valid
          @RequestBody AccountDto dto
  );

  /**
   * Updates an account with a specific id
   * The account's password will never be returned.
   *
   * @param id  the required id, must be set and greater than 0
   * @param dto a valid account to update, must be set
   * @return the updated account
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Updates an account with a specific id
          The account's password will never be returned.
          """)
  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(ADMINISTRATOR)
  AccountDto updateById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id,

          @Parameter(required = true, description = "a valid account to update, must be set")
          @Valid
          @RequestBody AccountDto dto
  );

  /**
   * Deletes an account with a specific id
   * Accounts are not really deleted, we will keep them linked to past reviews
   *
   * @param id the required id, must be set and greater than 0
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Deletes an account with a specific id.
          Accounts are not really deleted, we will keep them linked to past reviews
          """)
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(ADMINISTRATOR)
  void deleteById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id
  );
}
