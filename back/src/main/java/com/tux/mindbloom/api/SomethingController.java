package com.tux.mindbloom.api;

import com.tux.mindbloom.api.models.SomethingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.http.HttpStatus;
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

import static com.tux.mindbloom.config.Constants.Api.SOMETHING;
import static com.tux.mindbloom.config.Constants.Authority.NORMAL;

/**
 * Endpoints for some things
 */
@Tag(name = "Something")
@Validated
@RequestMapping(path = SOMETHING)
public interface SomethingController {

  /**
   * Returns all existing things
   *
   * @return all existing things
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Returns all existing things")
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  List<SomethingDto> findAll();

  /**
   * Returns something with a specific id
   *
   * @param id the required id, must be set and greater than 0
   * @return the found thing
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Returns something with a specific id")
  @GetMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  SomethingDto findById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id
  );

  /**
   * Creates something
   *
   * @param dto a valid something to create, must be set
   * @return the created thing
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Creates something")
  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(NORMAL)
  SomethingDto create(
          @Parameter(required = true, description = "a valid something to create, must be set")
          @Valid
          @RequestBody SomethingDto dto
  );

  /**
   * Updates something with a specific id
   *
   * @param id  the required id, must be set and greater than 0
   * @param dto a valid something to update, must be set
   * @return the updated thing
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Updates something with a specific id")
  @PutMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  SomethingDto updateById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id,

          @Parameter(required = true, description = "a valid something to update, must be set")
          @Valid
          @RequestBody SomethingDto dto
  );

  /**
   * Deletes something with a specific id
   *
   * @param id the required id, must be set and greater than 0
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Deletes something with a specific id")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void deleteById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0")
          @Min(1)
          @PathVariable Long id
  );
}
