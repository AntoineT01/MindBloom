package com.tanguylegoff.templateapp.business.impl;

import com.tanguylegoff.templateapp.business.AccountRequestService;
import com.tanguylegoff.templateapp.business.mappers.AccountMapper;
import com.tanguylegoff.templateapp.dao.db.AccountRepository;
import io.micrometer.common.util.StringUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * implementation of {@link UserDetailsService}
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  /**
   * Controls for accounts
   */
  private final AccountRepository accountRepository;

  /**
   * Controls for account requests
   */
  private final AccountRequestService accountRequestService;

  /**
   * Account / AccountDto mapping
   */
  private final AccountMapper mapper;

  /**
   * Injection constructor
   *
   * @param accountRepository     Controls for accounts
   * @param accountRequestService Controls for account requests
   * @param mapper                Account / AccountDto mapping
   */
  public UserDetailsServiceImpl(
    AccountRepository accountRepository,
    AccountRequestService accountRequestService,
    AccountMapper mapper
  ) {
    this.accountRepository = accountRepository;
    this.accountRequestService = accountRequestService;
    this.mapper = mapper;
  }

  /**
   * Loads a user (spring security), by searching for the existing account in the database
   *
   * @param mail the required mail, must be set
   * @return the built user detail
   * @throws UsernameNotFoundException if the account couldn't be found
   */
  @Override
  public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
    return accountRepository.findByMail(mail)
      .map(account -> {

          // Users without password should be users who did not finish
          // inscription procedure with mail. Anyway, we need a password.
          if (StringUtils.isEmpty(account.getPassword())) {
            throw new UsernameNotFoundException(mail);
          }

          if (Boolean.FALSE.equals(account.getActive())) {
            // Token should be down, sending a new token
            accountRequestService.requestMailVerification(mapper.toDto(account));
          }

          return new User(
            String.valueOf(account.getId()),
            account.getPassword(),
            account.getActive(),
            true,
            true,
            true,
            List.of(new SimpleGrantedAuthority(account.getProfile().getLabel()))
          );
        }
      )
      .orElseThrow(() -> new UsernameNotFoundException(mail));
  }
}