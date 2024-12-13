package com.tux.mindbloom.api.impl;

import com.tux.mindbloom.api.AbstractRestController;
import com.tux.mindbloom.api.MyAccountController;
import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.FormLoginDto;
import com.tux.mindbloom.business.AccountService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;

import static com.tux.mindbloom.config.Constants.Api.ACCOUNTS;
import static com.tux.mindbloom.config.Constants.Api.ME;
import static com.tux.mindbloom.config.Constants.Api.PASSWORD;

/**
 * implementation of {@link MyAccountController}
 */
@Slf4j
@RestController
public class MyAccountControllerImpl extends AbstractRestController implements MyAccountController {

  /**
   * Controls for accounts
   */
  private final AccountService accountService;

  /**
   * Injection constructor
   *
   * @param accountService Controls for accounts
   */
  public MyAccountControllerImpl(AccountService accountService) {
    this.accountService = accountService;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto getMyAccount() {
    Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    log.info("Rest : GET - {}{} : context id : {}", ACCOUNTS, ME, id);
    return logResponse(log, "GET", accountService.findById(id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto updateMyAccount(AccountDto dto) {
    Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    log.info("Rest : PUT - {}{} : context id : {}", ACCOUNTS, ME, id);
    logRequest(log, dto);

    AccountDto me = accountService.findById(id);

    // Prevent ourselves from changing stuff we shouldn't change (like profiles)
    me.setFirstname(dto.getFirstname());
    me.setLastname(dto.getLastname());
    me.setMail(dto.getMail().toLowerCase());
    me.setLocale(dto.getLocale());
    me.setOauthId(dto.getOauthId());
    me.setOauthProvider(dto.getOauthProvider());

    return logResponse(log, "PUT", accountService.updateById(me.getId(), me));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteMyAccount() {
    Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    log.info("Rest : DELETE - {}{} : context id : {}", ACCOUNTS, ME, id);

    accountService.deleteById(id);

    logResponse(log, "DELETE");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateMyPassword(FormLoginDto formLoginDto) {
    Long id = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getName());
    log.info("Rest : PUT - {}{}{} : context id : {}", ACCOUNTS, ME, PASSWORD, id);
    logRequest(log, formLoginDto);

    accountService.updatePassword(id, formLoginDto);

    logResponse(log, "PUT");
  }

  /**
   * {@inheritDoc}
   *
   * @param response
   */
  @Override
  public void logout(HttpServletResponse response) {
    log.info("Rest : POST - {}{} : Logging out", ACCOUNTS, ME);
    accountService.logout(response);
    log.info("Rest : POST - {}{} : Logout successful", ACCOUNTS, ME);
  }
}
