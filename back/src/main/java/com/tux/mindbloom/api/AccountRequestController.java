package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.AccountRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tux.mindbloom.config.Constants.Api.ACCOUNT_REQUESTS;
import static com.tux.mindbloom.config.Constants.Authority.NORMAL;


/**
 * Endpoints for account requests
 */
@Tag(name = "Authentication")
@RequestMapping(path = ACCOUNT_REQUESTS)
@Validated
public interface AccountRequestController {

  /**
   * Public endpoint for resetting passwords
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Public endpoint for account request creation.
          Allow for changing and resetting password
          """)
  @GetMapping("/reset")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void resetPassword(@Email @RequestParam String mail);

  /**
   * Endpoint for changing passwords.
   * Must be authenticated
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Public endpoint for account request creation.
          Allow for changing and resetting password
          """)
  @GetMapping("/change")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void changePassword();

  /**
   * Public endpoint for account request resolution.
   * May contain passwords in specific cases.
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
          Public endpoint for account request resolution.
          May contain passwords in specific cases.
          """)
  @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.NO_CONTENT)
  void resolveRequest(
          @Parameter(required = true, description = "a request resolution")
          @Valid
          @RequestBody AccountRequestDto body
  );
}
