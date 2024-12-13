package com.tanguylegoff.templateapp.api;

import com.tanguylegoff.templateapp.api.models.FormLoginDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import static com.tanguylegoff.templateapp.config.Constants.Api.LOGIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This controller's only purpose is to "show" Spring Security's form login processor url within Swagger.
 */
@Tag(name = "Authentication")
@RequestMapping(path = LOGIN)
public interface FormLoginController {

  /**
   * Exposes the login endpoint
   *
   * @param body an email and it's encrypted password
   */
  @Operation(summary = "Login", description = """
    Exemples de body :

    {
          "email": "admin@example.com",
          "password": "c7ad44cbad762a5da0a452f9e854fdc1e0e7a52a38015f23f3eab1d80b931dd472634dfac71cd34ebc35d16ab7fb8a90c81f975113d6c7538dc69dd8de9077ec" \
    }
    {
          "email": "user@example.com",
          "password": "ee330f2e66ac10ecf672491ca03201e8d3142625d3ee6059f19ca157cac58102ffa827ee41e56d63e9c4ca6ecf625b0adeae4f55ffb8a113001a8afa6fd9ea1f" \
    }
    """
  )
  @PostMapping(consumes = APPLICATION_JSON_VALUE)
  void formLoginProcessor(
    @Parameter(required = true, description = "a valid form login to connect with")
    @Valid
    @RequestBody FormLoginDto body
  );
}
