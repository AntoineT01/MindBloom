package com.tanguylegoff.templateapp.business.impl;

import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.business.AccountRequestService;
import com.tanguylegoff.templateapp.business.mappers.AccountMapper;
import com.tanguylegoff.templateapp.dao.db.AccountRepository;
import com.tanguylegoff.templateapp.dao.db.entities.Account;
import com.tanguylegoff.templateapp.dao.db.entities.AccountData;
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
      when(accountRepository.findByHandle(account.getHandle())).thenReturn(Optional.of(account));

      UserDetails result = service.loadUserByUsername("Normal");

      assertThat(result)
              .isNotNull()
              .extracting(UserDetails::getUsername, UserDetails::getPassword, UserDetails::isEnabled)
              .contains(account.getId().toString(), account.getPassword(), account.getActive());

      verify(accountRequestService, never()).requestMailVerification(any(AccountDto.class));
    }


    @Test
    void ShouldSendVerificationMailIfTheLoadedAccountIsInactive() {
      account.setActive(false);
      when(accountRepository.findByHandle(account.getHandle())).thenReturn(Optional.of(account));

      UserDetails result = service.loadUserByUsername("Normal");

      assertThat(result)
              .isNotNull()
              .extracting(UserDetails::getUsername, UserDetails::getPassword, UserDetails::isEnabled)
              .contains(account.getId().toString(), account.getPassword(), account.getActive());

      verify(accountRequestService, times(1)).requestMailVerification(any(AccountDto.class));
    }

    @Test
    void ShouldLoadUserOnlyIfItExists() {
      when(accountRepository.findByHandle(account.getHandle())).thenReturn(Optional.empty());

      assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("Normal"));

      verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void ShouldLoadUserOnlyIfItHasFinishedSigningUpAndHasAPassword() {
      account.setPassword(null);
      when(accountRepository.findByHandle(account.getHandle())).thenReturn(Optional.of(account));

      assertThrows(UsernameNotFoundException.class, () -> service.loadUserByUsername("Normal"));
    }
  }
}