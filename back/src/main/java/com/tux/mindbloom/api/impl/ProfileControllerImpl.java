package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.ProfileController;
import com.tux.mindbloom.api.models.ProfileDto;
import com.tux.mindbloom.business.ProfileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Api.PROFILES;

/**
 * implementation of {@link ProfileController}
 */
@Slf4j
@RestController
public class ProfileControllerImpl extends AbstractRestController implements ProfileController {

  /**
   * Controls for profiles
   */
  private final ProfileService profileService;

  /**
   * Injection constructor
   *
   * @param profileService Controls for accounts
   */
  public ProfileControllerImpl(ProfileService profileService) {
    this.profileService = profileService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProfileDto> findAll() {
    log.info("Rest : GET - {}", PROFILES);
    return logResponse(log, "GET", profileService.findAll());
  }
}
