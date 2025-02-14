package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.business.AccountRequestService;
import com.tux.mindbloom.business.mappers.AccountMapper;
import com.tux.mindbloom.dao.db.AccountRepository;
import com.tux.mindbloom.dao.db.entities.Account;
import com.tux.mindbloom.dao.db.entities.AccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {
  private UserDetailsService service;

  @Mock
  private AccountRepository accountRepository;

  @Mock
  private AccountRequestService accountRequestService;

  private Account account;

  @BeforeEach
  public void setup() {
    service = new UserDetailsServiceImpl(
            accountRepository,
            accountRequestService,
            Mappers.getMapper(AccountMapper.class)
    );

    account = AccountData.getNormal();
  }

  @Nested
  class Authenticate {

    @Test
    void ShouldLoadUser() {
      when(accountRepository.findByMail(account.getMail())).thenReturn(Optional.of(account));

      UserDetails result = service.loadUserByUsername("whatever@example.com");

      assertThat(result)
              .isNotNull()
              .extracting(UserDetails::getUsername, UserDetails::getPassword, UserDetails::isEnabled)
              .contains(account.getId().toString(), account.getPassword(), account.getActive());

      verify(accountRequestService, never()).requestMailVerification(any(AccountDto.class));
    }


    @Test
    void ShouldSendVerificationMailIfTheLoadedAccountIsInactive() {
      when(accountRepository.findByMail(account.getMail())).thenReturn(Optional.of(account));
      account.setActive(false);

      UserDetails result = service.loadUserByUsername("whatever@example.com");

      assertThat(result)
              .isNotNull()
              .extracting(UserDetails::getUsername, UserDetails::getPassword, UserDetails::isEnabled)
              .contains(account.getId().toString(), account.getPassword(), account.getActive());

      verify(accountRequestService, times(1)).requestMailVerification(any(AccountDto.class));
    }

    @Test
    void ShouldLoadUserOnlyIfItExists() {

      assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("Normal"));

      verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void ShouldLoadUserOnlyIfItHasFinishedSigningUpAndHasAPassword() {
      account.setPassword(null);

      assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("Normal"));
    }
  }
}