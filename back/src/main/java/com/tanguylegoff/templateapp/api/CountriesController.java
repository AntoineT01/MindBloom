package com.tanguylegoff.templateapp.api;

import com.tanguylegoff.templateapp.api.models.CountryDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

import static com.tanguylegoff.templateapp.config.Constants.Api.COUNTRIES;
import static com.tanguylegoff.templateapp.config.Constants.Authority.NORMAL;


/**
 * Endpoints for countries
 */
@Tag(name = "All Countries")
@RequestMapping(path = COUNTRIES)
@Validated
public interface CountriesController {

  /**
   * Returns all existing countries
   *
   * @param maxId the filter id, if set, must be greater than 0
   * @return all existing countries
   */
  @Operation(summary = "Returns all existing countries. A filter can be applied to limit the results," +
          "by setting the maxId parameter to the maximum id to return.")
  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  List<CountryDto> findAll(
          @Parameter(example = "3", description = "the filter id. If set, must be greater than 0.")
          @Min(1)
          @RequestParam(required = false) Integer maxId
  );

  /**
   * Returns a country with a specific id
   *
   * @param id the required id, must be set and greater than 0
   * @return the found thing
   */
  @Operation(summary = "Returns a country with a specific id.")
  @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  CountryDto findById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0.")
          @Min(1)
          @PathVariable Long id
  );

  /**
   * Creates a new country
   *
   * @param dto a country to create
   * @return the created country
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Creates a new country.")
  @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize(NORMAL)
  CountryDto create(
          @Parameter(required = true, description = "a country to create")
          @Valid
          @RequestBody CountryDto dto
  );

  /**
   * Updates a country with a specific id
   *
   * @param id  the required id, must be set and greater than 0
   * @param dto a country to update
   * @return the updated country
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Updates a country with a specific id.")
  @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.OK)
  @PreAuthorize(NORMAL)
  CountryDto updateById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0.")
          @Min(1)
          @PathVariable Long id,

          @Parameter(required = true, description = "a country to update")
          @Valid
          @RequestBody CountryDto dto
  );

  /**
   * Deletes a country with a specific id
   *
   * @param id the required id, must be set and greater than 0
   */
  @Operation(security = {@SecurityRequirement(name = "bearer-key")}, summary = "Deletes a country with a specific id.")
  @DeleteMapping(path = "/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @PreAuthorize(NORMAL)
  void deleteById(
          @Parameter(required = true, example = "1", description = "the required id, must be set and greater than 0.")
          @Min(1)
          @PathVariable Long id
  );
}
