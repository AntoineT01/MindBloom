package com.tanguylegoff.templateapp.business;

import com.tanguylegoff.templateapp.api.models.SomethingDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Controls for some things
 */
@Transactional
@Validated
public interface SomethingService {

  /**
   * Returns all existing things
   *
   * @return all existing things
   */
  List<SomethingDto> findAll();

  /**
   * Returns something with a specific id
   *
   * @param id the required id, must be set
   * @return the found thing
   * @throws EntityNotFoundException if something couldn't be found
   */
  SomethingDto findById(@NotNull Long id);

  /**
   * Creates something
   *
   * @param dto a valid something to create, must be set
   * @return the created thing
   */
  SomethingDto create(@Valid @NotNull SomethingDto dto);

  /**
   * Updates something with a specific id
   *
   * @param id  the required id, must be set
   * @param dto a valid something to create, must be set
   * @return the updated thing
   * @throws EntityNotFoundException if something couldn't be found
   */
  SomethingDto updateById(@NotNull Long id, @Valid @NotNull SomethingDto dto);

  /**
   * Deletes something with a specific id
   *
   * @param id the required id, must be set
   * @throws EntityNotFoundException if something couldn't be found
   */
  void deleteById(@NotNull Long id);
}
