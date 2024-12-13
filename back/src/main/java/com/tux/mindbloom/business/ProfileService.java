package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.ProfileDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.NotNull;

import java.util.List;

/**
 * Controls for profiles
 */
@Transactional
public interface ProfileService {

  /**
   * Returns all existing profiles
   *
   * @return all existing profiles
   */
  List<ProfileDto> findAll();

  /**
   * Finds the profile with a specific label
   *
   * @param label the required label, must be set
   * @return the found profile
   * @throws EntityNotFoundException if the profile couldn't be found
   */
  ProfileDto findByLabel(@NotNull String label);
}
