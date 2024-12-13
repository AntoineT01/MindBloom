package com.tanguylegoff.templateapp.business.impl;

import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.api.models.AccountDtoData;
import com.tanguylegoff.templateapp.api.models.AccountRequestDto;
import com.tanguylegoff.templateapp.api.models.AccountRequestDtoData;
import com.tanguylegoff.templateapp.business.AccountRequestService;
import com.tanguylegoff.templateapp.business.EmailService;
import com.tanguylegoff.templateapp.business.mappers.AccountMapper;
import com.tanguylegoff.templateapp.config.AppConfig;
import com.tanguylegoff.templateapp.config.exceptions.BadArgumentException;
import com.tanguylegoff.templateapp.config.exceptions.EntityNotFoundException;
import com.tanguylegoff.templateapp.dao.db.AccountRepository;
import com.tanguylegoff.templateapp.dao.db.AccountRequestRepository;
import com.tanguylegoff.templateapp.dao.db.entities.Account;
import com.tanguylegoff.templateapp.dao.db.entities.AccountRequest;
import com.tanguylegoff.templateapp.dao.db.entities.AccountRequestData;
import com.tanguylegoff.templateapp.dao.db.entities.RequestType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Locale;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountRequestServiceImplTest {
  private AccountRequestService service;

  @Mock
  private AccountRequestRepository repository;

  @Mock
  private EmailService emailService;

  @Mock
  private AccountRepository accountRepository;

  private AccountRequestDto tokenDto;

  @Mock
  private AppConfig appConfig;

  @BeforeEach
  public void setup() {
    service = new AccountRequestServiceImpl(repository, emailService, Mappers.getMapper(AccountMapper.class), accountRepository, appConfig);

    tokenDto = AccountRequestDtoData.getToken();
  }

  @Nested
  class ResolveRequest {

    @Captor
    ArgumentCaptor<Account> captor;

    private AccountRequest accountRequest;

    @Nested
    class VerifyMail {

      @BeforeEach
      public void setup() {
        accountRequest = AccountRequestData.getVerificationRequest();
      }

      @Test
      void ShouldVerifyMail() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        service.resolveRequest(tokenDto);

        verify(accountRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue())
          .isNotNull()
          .extracting(Account::getActive)
          .isEqualTo(true);
      }

      @Test
      void ShouldVerifyMailOnlyIfyRequestExists() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldVerifyMailOnlyIfyRequestIsNotExpired() {
        accountRequest.setExpirationDate(AccountRequestData.PAST_DATE);
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }
    }

    @Nested
    class ChangePassword {

      @BeforeEach
      public void setup() {
        accountRequest = AccountRequestData.getChangePasswordRequest();
      }

      @Test
      void ShouldChangePassword() {
        // Copy password, else it will be changed by reference and control will be useless.
        String previousPassword = accountRequest.getAccount().getPassword();
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        service.resolveRequest(tokenDto);

        verify(accountRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue())
          .isNotNull()
          .extracting(Account::getActive)
          .isEqualTo(accountRequest.getAccount().getActive());

        assertThat(captor.getValue().getPassword()).isNotEqualTo(previousPassword);

      }

      @Test
      void ShouldChangePasswordOnlyIfyRequestExists() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldChangePasswordOnlyIfRequestIsNotExpired() {
        accountRequest.setExpirationDate(AccountRequestData.PAST_DATE);
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldChangePasswordOnlyIfPasswordIsGiven() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));
        tokenDto.setPassword(null);

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }
    }

    @Nested
    class ResetPassword {

      @BeforeEach
      public void setup() {
        accountRequest = AccountRequestData.getResetPasswordRequest();
      }

      @Test
      void ShouldResetPassword() {
        // Copy password, else it will be changed by reference and control will be useless.
        String previousPassword = accountRequest.getAccount().getPassword();
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        service.resolveRequest(tokenDto);

        verify(accountRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue())
          .isNotNull()
          .extracting(Account::getActive)
          .isEqualTo(accountRequest.getAccount().getActive());

        assertThat(captor.getValue().getPassword()).isNotEqualTo(previousPassword);

      }

      @Test
      void ShouldResetPasswordOnlyIfRequestExists() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldResetPasswordOnlyIfRequestIsNotExpired() {
        accountRequest.setExpirationDate(AccountRequestData.PAST_DATE);
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldChangePasswordOnlyIfPasswordIsGiven() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));
        tokenDto.setPassword(null);

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }
    }

    @Nested
    class FinishSignup {

      @BeforeEach
      public void setup() {
        accountRequest = AccountRequestData.getSignupRequest();
        accountRequest.getAccount().setActive(false);
      }

      @Test
      void ShouldFinishSigningUp() {
        // Copy password, else it will be changed by reference and control will be useless.
        String previousPassword = accountRequest.getAccount().getPassword();
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        service.resolveRequest(tokenDto);

        verify(accountRepository, times(1)).save(captor.capture());
        assertThat(captor.getValue())
          .isNotNull()
          .extracting(Account::getActive)
          .isEqualTo(true);

        assertThat(captor.getValue().getPassword()).isNotEqualTo(previousPassword);

      }

      @Test
      void ShouldFinishSigningUpOnlyIfRequestExists() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldFinishSigningUpOnlyIfRequestIsNotExpired() {
        accountRequest.setExpirationDate(AccountRequestData.PAST_DATE);
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }

      @Test
      void ShouldChangePasswordOnlyIfPasswordIsGiven() {
        when(repository.findByToken(tokenDto.getToken())).thenReturn(Optional.of(accountRequest));
        tokenDto.setPassword(null);

        assertThrows(BadArgumentException.class, () -> service.resolveRequest(tokenDto));

        verify(accountRepository, never()).save(any(Account.class));
      }
    }
  }

  @Nested
  class NewRequest {

    @Captor
    ArgumentCaptor<AccountRequest> requestCaptor;
    @Captor
    ArgumentCaptor<Locale> localeCaptor;
    @Captor
    ArgumentCaptor<String> mailCaptor;
    @Captor
    ArgumentCaptor<Map<String, Object>> argCaptor;
    private AccountDto accountDto;

    @BeforeEach
    public void setup() {
      accountDto = AccountDtoData.getNormal();
    }

    @Test
    void ShouldAskForMailVerification() {
      service.requestMailVerification(accountDto);

      verify(repository, times(1)).save(requestCaptor.capture());
      verify(emailService, times(1)).sendVerifyMail(localeCaptor.capture(), mailCaptor.capture(), argCaptor.capture());

      controlArgs(RequestType.VERIFYMAIL);
    }

    @Test
    void ShouldAskChangingPassword() {
      service.requestPasswordModification(accountDto);

      verify(repository, times(1)).save(requestCaptor.capture());
      verify(emailService, times(1)).sendChangePasswordMail(localeCaptor.capture(), mailCaptor.capture(), argCaptor.capture());

      controlArgs(RequestType.CHANGE);
    }

    @Test
    void ShouldAskResetPassword() {
      service.requestPasswordReset(accountDto);

      verify(repository, times(1)).save(requestCaptor.capture());
      verify(emailService, times(1)).sendResetPasswordMail(localeCaptor.capture(), mailCaptor.capture(), argCaptor.capture());

      controlArgs(RequestType.RESET);
    }

    @Test
    void ShouldAskFinishSignup() {
      service.requestPasswordInitialSetup(accountDto);

      verify(repository, times(1)).save(requestCaptor.capture());
      verify(emailService, times(1)).sendSetupPassword(localeCaptor.capture(), mailCaptor.capture(), argCaptor.capture());

      controlArgs(RequestType.SIGNUP);
    }

    private void controlArgs(RequestType signup) {
      // Peut-être un peu overkill de faire tous ces contrôles.
      // Au grand minimum, on veut controller que le token dans requestCaptor est le même que celui dans argCaptor.
      AccountRequest request = requestCaptor.getValue();
      assertThat(request).isNotNull().extracting(AccountRequest::getRequestType).isEqualTo(signup);
      assertThat(request.getExpirationDate()).isInTheFuture();
      assertThat(request.getToken()).isNotNull();
      assertThat(request.getAccount().getId()).isEqualTo(accountDto.getId());

      assertThat(localeCaptor.getValue()).isNotNull().isEqualTo(Locale.FRENCH);

      assertThat(mailCaptor.getValue()).isNotNull().isEqualTo(accountDto.getMail());

      Map<String, Object> args = argCaptor.getValue();
      assertThat(args).isNotNull().hasSize(3)
        .containsEntry("firstname", accountDto.getFirstname())
        .containsEntry("lastname", accountDto.getLastname())
        .containsEntry("token", request.getToken());
    }
  }
}