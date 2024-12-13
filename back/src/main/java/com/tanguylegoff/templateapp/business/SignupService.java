package com.tanguylegoff.templateapp.business;

import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.api.models.SignupDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

/**
 * Controls for signups
 */
@Transactional
@Validated
public interface SignupService {

  /**
   * Creates an account, but with a password
   *
   * @param dto a valid account to create, must be set
   * @return the created account
   */
  AccountDto signup(@Valid @NotNull SignupDto dto);

}
