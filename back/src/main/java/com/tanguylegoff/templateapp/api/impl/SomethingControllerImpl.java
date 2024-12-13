package com.tanguylegoff.templateapp.api.impl;

import com.tanguylegoff.templateapp.api.AbstractRestController;
import com.tanguylegoff.templateapp.api.SomethingController;
import com.tanguylegoff.templateapp.api.models.SomethingDto;
import com.tanguylegoff.templateapp.business.SomethingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tanguylegoff.templateapp.config.Constants.Api.SOMETHING;

/**
 * implementation of {@link SomethingController}
 */
@Slf4j
@RestController
public class SomethingControllerImpl extends AbstractRestController implements SomethingController {

  /**
   * Controls for something
   */
  private final SomethingService somethingService;

  /**
   * Injection constructor
   *
   * @param somethingService Controls for something
   */
  public SomethingControllerImpl(SomethingService somethingService) {
    this.somethingService = somethingService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<SomethingDto> findAll() {
    log.info("Rest : GET - {}", SOMETHING);
    return logResponse(log, "GET", somethingService.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SomethingDto findById(Long id) {
    log.info("Rest : GET - {}/{}", SOMETHING, id);
    return logResponse(log, "GET", somethingService.findById(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SomethingDto create(SomethingDto dto) {
    log.info("Rest : POST - {}", SOMETHING);
    logRequest(log, dto);
    return logResponse(log, "POST", somethingService.create(dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SomethingDto updateById(Long id, SomethingDto dto) {
    log.info("Rest : PUT - {}/{}", SOMETHING, id);
    logRequest(log, dto);
    return logResponse(log, "PUT", somethingService.updateById(id, dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    log.info("Rest : DELETE - {}/{}", SOMETHING, id);
    somethingService.deleteById(id);
    logResponse(log, "DELETE");
  }
}
