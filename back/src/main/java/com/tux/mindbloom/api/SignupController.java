package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.SignupDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.tux.mindbloom.config.Constants.Api.SIGNUPS;


/**
 * Endpoints for account creation
 */
@Tag(name = "Authentication")
@RequestMapping(path = SIGNUPS)
@Validated
public interface SignupController {

  /**
   * Public endpoint for signup.
   * Profile defaulted to normal
   *
   * @return the created account
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = """
    Public endpoint for signup.
    Profile defaulted to normal
    """)
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  AccountDto signup(
    @Parameter(required = true, description = "a account to create")
    @Valid
    @RequestBody SignupDto body
  );
}
