package com.tanguylegoff.templateapp.api.impl;

import com.tanguylegoff.templateapp.api.AbstractRestController;
import com.tanguylegoff.templateapp.api.AccountController;
import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.business.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.tanguylegoff.templateapp.config.Constants.Api.ACCOUNTS;

/**
 * implementation of {@link AccountController}
 */
@Slf4j
@RestController
public class AccountControllerImpl extends AbstractRestController implements AccountController {

  /**
   * Controls for accounts
   */
  private final AccountService accountService;

  /**
   * Injection constructor
   *
   * @param accountService Controls for accounts
   */
  public AccountControllerImpl(AccountService accountService) {
    this.accountService = accountService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AccountDto> findAll() {
    log.info("Rest : GET - {}", ACCOUNTS);
    return logResponse(log, "GET", accountService.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto findById(Long id) {
    log.info("Rest : GET - {}/{}", ACCOUNTS, id);
    return logResponse(log, "GET", accountService.findById(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto create(AccountDto dto) {
    log.info("Rest : POST - {}", ACCOUNTS);
    logRequest(log, dto);
    return logResponse(log, "POST", accountService.create(dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto updateById(Long id, AccountDto dto) {
    log.info("Rest : PUT - {}/{}", ACCOUNTS, id);
    logRequest(log, dto);

    return logResponse(log, "PUT", accountService.updateById(dto.getId(), dto));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    log.info("Rest : DELETE - {}/{}", ACCOUNTS, "me");
    accountService.deleteById(id);
    logResponse(log, "DELETE");
  }
}
