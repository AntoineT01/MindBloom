package com.tux.mindbloom.config.database;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

/**
 * Used by jpa to automate jpa auditing.
 * This contains the currently connected user id and will be used for fields created_by and updated_by in entity.
 */
public class AuditorAwareTest implements AuditorAware<Long> {

  /**
   * Used for tests, contains a fake connected user id.
   *
   * @return a fake connected user id
   */
  @Override
  @Nonnull
  public Optional<Long> getCurrentAuditor() {
    return Optional.of(1L);
  }

}
