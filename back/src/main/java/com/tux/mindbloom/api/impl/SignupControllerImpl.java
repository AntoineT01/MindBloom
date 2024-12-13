package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.MyAccountController;
import com.tux.mindbloom.api.SignupController;
import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.SignupDto;
import com.tux.mindbloom.business.SignupService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import static com.tux.mindbloom.config.Constants.Api.SIGNUPS;

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
