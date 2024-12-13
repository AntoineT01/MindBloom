package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.ProfileDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.PROFILES;

/**
 * Endpoints for profiles
 */
@Tag(name = "Profile")
@RequestMapping(path = PROFILES)
public interface ProfileController {

  /**
   * Returns all existing profiles
   *
   * @return all existing profiles
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Returns all existing profiles")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<ProfileDto> findAll();

}
