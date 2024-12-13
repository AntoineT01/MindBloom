package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.AccountDtoData;
import com.tux.mindbloom.api.models.SignupDto;
import com.tux.mindbloom.api.models.SignupDtoData;
import com.tux.mindbloom.business.AccountRequestService;
import com.tux.mindbloom.business.SignupService;
import com.tux.mindbloom.business.mappers.AccountMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.config.exceptions.HandleAlreadyExistsException;
import com.tux.mindbloom.dao.db.AccountRepository;
import com.tux.mindbloom.dao.db.ProfileRepository;
import com.tux.mindbloom.dao.db.entities.Account;
import com.tux.mindbloom.dao.db.entities.AccountData;
import com.tux.mindbloom.dao.db.entities.Profile;
import com.tux.mindbloom.dao.db.entities.ProfileData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SignupServiceImplTest {

  @Captor
  ArgumentCaptor<Account> captor;
  private SignupService service;
  @Mock
  private AccountRequestService accountRequestService;
  @Mock
  private AccountRepository accountRepository;
  @Mock
  private ProfileRepository profileRepository;
  private SignupDto signupDto;

  private Profile profile;

  @BeforeEach
  public void setup() {
    service = new SignupServiceImpl(
            accountRepository,
            profileRepository,
            Mappers.getMapper(AccountMapper.class),
            accountRequestService
    );

    signupDto = SignupDtoData.getAccount();
    profile = ProfileData.getNormal();
  }


  @Nested
  class Signup {
    @Test
    void ShouldSignup() {
      when(accountRepository.save(any(Account.class))).thenReturn(AccountData.getNormal());

      when(profileRepository.findByLabel(anyString())).thenReturn(Optional.of(profile));
      signupDto.setMail("WHATEVER@EXAMPLE.COM");

      AccountDto result = service.signup(signupDto);

      assertThat(result).isEqualTo(AccountDtoData.getNormal());
      verify(accountRepository, times(1)).save(captor.capture());
      Account saved = captor.getValue();
      assertThat(saved)
              .isNotNull()
              .extracting(Account::getMail, Account::getProfile, Account::getActive)
              .contains("whatever@example.com", profile, false);

      assertThat(saved.getPassword()).isNotEqualTo(signupDto.getPassword());
    }

    @Test
    void ShouldProceedOnlyIfNormalProfileStillExists() {
      assertThrows(EntityNotFoundException.class, () -> service.signup(signupDto));

      verify(accountRepository, never()).save(any(Account.class));
    }

    @Test
    void ShouldProceedOnlyIfHandleDoesNotAlreadyExist() {
      when(accountRepository.findByHandle(signupDto.getHandle())).thenReturn(Optional.of(AccountData.getNormal()));
      assertThrows(HandleAlreadyExistsException.class, () -> service.signup(signupDto));

      verify(accountRepository, never()).save(any(Account.class));
    }
  }
}