package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.CountryDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Controls for accounts
 */
@Transactional
@Validated
public interface CountriesService {
  /**
   * Find all countries
   *
   * @param maxId the maximum id to filter the list
   * @return the list of countries
   */
  List<CountryDto> findAll(Integer maxId);

  /**
   * Find a country by its id
   *
   * @param id the id of the country
   * @return the country
   * @throws EntityNotFoundException if the country is not found
   */
  CountryDto findById(@NotNull Long id) throws EntityNotFoundException;

  /**
   * Create a country
   *
   * @param country the country to create
   * @return the created country
   */
  CountryDto create(@Valid CountryDto country);

  /**
   * Update a country
   *
   * @param id      the id of the country to update
   * @param country the country to update
   * @return the updated country
   * @throws EntityNotFoundException if the country is not found
   */
  CountryDto updateById(@NotNull Long id, @Valid CountryDto country) throws EntityNotFoundException;

  /**
   * Delete a country
   *
   * @param id the id of the country to delete
   * @throws EntityNotFoundException if the country is not found
   */
  void deleteById(@NotNull Long id) throws EntityNotFoundException;
}
