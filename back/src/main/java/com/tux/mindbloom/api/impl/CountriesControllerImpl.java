package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.CountriesController;
import com.tux.mindbloom.api.models.CountryDto;
import com.tux.mindbloom.business.CountriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.COUNTRIES;

/**
 * implementation of {@link CountriesController}
 */
@Slf4j
@RestController
public class CountriesControllerImpl extends AbstractRestController implements CountriesController {
  /**
   * Controls for countries
   */
  private final CountriesService countriesService;

  /**
   * Injection constructor
   */
  public CountriesControllerImpl(CountriesService countriesService) {
    this.countriesService = countriesService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CountryDto> findAll(Integer maxId) {
    log.info("Rest : GET - {}", maxId != null ? COUNTRIES + "?maxId=" + maxId : COUNTRIES);
    return logResponse(log, "GET", countriesService.findAll(maxId));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CountryDto findById(Long id) {
    log.info("Rest : GET - {}/{}", COUNTRIES, id);
    return logResponse(log, "GET", countriesService.findById(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CountryDto create(CountryDto dto) {
    log.info("Rest : POST - {}", COUNTRIES);
    logRequest(log, dto);
    return logResponse(log, "POST", countriesService.create(dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CountryDto updateById(Long id, CountryDto dto) {
    log.info("Rest : PUT - {}/{}", COUNTRIES, id);
    logRequest(log, dto);
    return logResponse(log, "PUT", countriesService.updateById(id, dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    log.info("Rest : DELETE - {}/{}", COUNTRIES, id);
    countriesService.deleteById(id);
    logResponse(log, "DELETE");
  }
}
