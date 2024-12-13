package com.tanguylegoff.templateapp.api.impl;

import com.tanguylegoff.templateapp.api.AbstractRestController;
import com.tanguylegoff.templateapp.api.MyAccountController;
import com.tanguylegoff.templateapp.api.SignupController;
import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.api.models.SignupDto;
import com.tanguylegoff.templateapp.business.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import static com.tanguylegoff.templateapp.config.Constants.Api.SIGNUPS;

/**
 * implementation of {@link MyAccountController}
 */
@Slf4j
@RestController
public class SignupControllerImpl extends AbstractRestController implements SignupController {

  /**
   * Controls for signups
   */
  private final SignupService signupService;

  /**
   * Injection constructor
   *
   * @param signupService Controls for signups
   */
  public SignupControllerImpl(SignupService signupService) {
    this.signupService = signupService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto signup(SignupDto body) {
    log.info("Rest : POST - {}", SIGNUPS);
    logRequest(log, body);
    return logResponse(log, "POST", signupService.signup(body));
  }
}
