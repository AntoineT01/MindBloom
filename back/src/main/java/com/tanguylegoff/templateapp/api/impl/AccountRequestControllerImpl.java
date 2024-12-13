package com.tanguylegoff.templateapp.api.impl;

import com.tanguylegoff.templateapp.api.AbstractRestController;
import com.tanguylegoff.templateapp.api.AccountRequestController;
import com.tanguylegoff.templateapp.api.MyAccountController;
import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.api.models.AccountRequestDto;
import com.tanguylegoff.templateapp.business.AccountRequestService;
import com.tanguylegoff.templateapp.business.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import static com.tanguylegoff.templateapp.config.Constants.Api.ACCOUNT_REQUESTS;

/**
 * implementation of {@link MyAccountController}
 */
@Slf4j
@RestController
public class AccountRequestControllerImpl extends AbstractRestController implements AccountRequestController {

  /**
   * Controls for signups
   */
  private final AccountRequestService accountRequestService;

  /**
   * Controls for accounts
   */
  private final AccountService accountService;

  /**
   * Injection constructor
   *
   * @param accountRequestService Controls for signups
   * @param accountService        Controls for accounts
   */
  public AccountRequestControllerImpl(
          AccountRequestService accountRequestService,
          AccountService accountService
  ) {
    this.accountRequestService = accountRequestService;
    this.accountService = accountService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resetPassword(String mail) {
    log.info("Rest : GET - {}/reset?mail={}", ACCOUNT_REQUESTS, mail);

    accountService.findAllByMail(mail.toLowerCase())
            .forEach(accountRequestService::requestPasswordReset);

    logResponse(log, "GET");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void changePassword() {
    Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    log.info("Rest : GET - {}/change : context id : {}", ACCOUNT_REQUESTS, id);

    AccountDto me = accountService.findById(id);

    accountRequestService.requestPasswordModification(me);
    logResponse(log, "GET");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resolveRequest(AccountRequestDto body) {
    log.info("Rest : POST - {}", ACCOUNT_REQUESTS);
    logRequest(log, body);
    accountRequestService.resolveRequest(body);
    logResponse(log, "POST");
  }
}
