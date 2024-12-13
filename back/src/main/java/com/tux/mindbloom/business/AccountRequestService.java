package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.AccountRequestDto;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

/**
 * Controls for profiles
 */
@Transactional
public interface AccountRequestService {

  /**
   * Sends a mail for setting up password for signups
   *
   * @param dto the account we need to set the password of
   */
  void requestPasswordInitialSetup(@NotNull AccountDto dto);

  /**
   * Sends a mail for changing an account's password
   *
   * @param dto the account we need to change the password of
   */
  void requestPasswordModification(@NotNull AccountDto dto);

  /**
   * Sends a mail for resetting an account's password
   *
   * @param dto the account we need to reset the password of
   */
  void requestPasswordReset(@NotNull AccountDto dto);

  /**
   * Sends a mail for verification of mail address
   *
   * @param dto the account we need to verify the mail of
   */
  void requestMailVerification(@NotNull AccountDto dto);

  /**
   * Resolve a request
   *
   * @param dto the resolution
   */
  void resolveRequest(@Valid @NotNull AccountRequestDto dto);
}
