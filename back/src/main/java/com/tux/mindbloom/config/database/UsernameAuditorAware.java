package com.tux.mindbloom.config.database;

import jakarta.annotation.Nonnull;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

/**
 * Used by jpa to automate jpa auditing.
 * This contains the currently connected user id and will be used for fields created_by and updated_by in entity.
 */
public class UsernameAuditorAware implements AuditorAware<Long> {

  /**
   * This contains the currently connected user id and will be used for fields created_by and updated_by in entity.
   *
   * @return the currently connected user id
   */
  @Override
  @Nonnull
  public Optional<Long> getCurrentAuditor() {
    String id = SecurityContextHolder.getContext().getAuthentication().getName();
    if (id == null) {
      return Optional.empty();
    }
    return Optional.of(Long.valueOf(id));
  }
}
