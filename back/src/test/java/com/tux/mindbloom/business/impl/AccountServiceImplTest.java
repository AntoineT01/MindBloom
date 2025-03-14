package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.AccountDtoData;
import com.tux.mindbloom.business.AccountRequestService;
import com.tux.mindbloom.business.AccountService;
import com.tux.mindbloom.business.mappers.AccountMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.config.exceptions.MailAlreadyExistsException;
import com.tux.mindbloom.dao.db.AccountRepository;
import com.tux.mindbloom.dao.db.entities.Account;
import com.tux.mindbloom.dao.db.entities.AccountData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {

  private AccountService service;

  @Mock
  private AccountRequestService accountRequestService;

  @Mock
  private AccountRepository repository;

  private Account account;

  private AccountDto accountDto;

  @BeforeEach
  public void setup() {
    service = new AccountServiceImpl(repository, Mappers.getMapper(AccountMapper.class), accountRequestService);

    accountDto = AccountDtoData.getNormal();
    account = AccountData.getNormal();
  }

  @Nested
  class FindById {
    @Test
    void ShouldFindById() {
      when(repository.findById(anyLong())).thenReturn(Optional.of(account));

      AccountDto result = service.findById(1L);

      assertThat(result).isEqualTo(accountDto);
      verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void shouldOnlyFindWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }
  }

  @Nested
  class FindAll {
    @Test
    void ShouldFindAll() {
      when(repository.findAll()).thenReturn(AccountData.getList());

      List<AccountDto> result = service.findAll();

      assertThat(result).isEqualTo(AccountDtoData.getList());
      verify(repository, times(1)).findAll();
    }
  }

  @Nested
  class Create {
    @Test
    void ShouldCreate() {
      when(repository.save(any(Account.class))).thenReturn(account);

      AccountDto result = service.create(accountDto);

      assertThat(result).isEqualTo(accountDto);
      verify(repository, times(1)).save(any(Account.class));
      verify(accountRequestService, times(1)).requestMailVerification(any(AccountDto.class));
    }

    @Test
    void ShouldCreateOnlyIfHandleDoesNotAlreadyExists() {
      when(repository.findByMail(account.getMail())).thenReturn(Optional.of(account));

      assertThrows(MailAlreadyExistsException.class, () -> service.create(accountDto));

      verify(repository, never()).save(any(Account.class));
      verify(accountRequestService, never()).requestMailVerification(any(AccountDto.class));
    }
  }

  @Nested
  class UpdateById {

    @Test
    void ShouldUpdateById() {
      when(repository.findById(anyLong())).thenReturn(Optional.of(account));
      when(repository.save(any(Account.class))).thenReturn(account);

      AccountDto result = service.updateById(1L, accountDto);

      assertThat(result).isEqualTo(accountDto);
      verify(repository, times(1)).save(any(Account.class));
      verify(accountRequestService, never()).requestMailVerification(any(AccountDto.class));
    }

    @Test
    void ShouldUpdateByIdAndSendAVerificationMail() {
      accountDto.setMail("some@other.mail");
      when(repository.findById(anyLong())).thenReturn(Optional.of(account));
      when(repository.save(any(Account.class))).thenReturn(account);

      AccountDto result = service.updateById(1L, accountDto);

      assertThat(result).isEqualTo(accountDto);
      verify(repository, times(1)).save(any(Account.class));
      verify(accountRequestService, times(1)).requestMailVerification(any(AccountDto.class));
    }

    @Test
    void shouldOnlyUpdateWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.updateById(1L, accountDto));

      verify(repository, never()).save(any(Account.class));
    }
  }

  @Nested
  class Delete {
    @Captor
    ArgumentCaptor<Account> captor;

    @Test
    void shouldDeleteById() {
      when(repository.findById(1L)).thenReturn(Optional.of(account));

      service.deleteById(1L);

      verify(repository, times(1)).save(captor.capture());
      assertThat(captor.getValue())
              .isNotNull()
              .extracting(
                      Account::getFirstname,
                      Account::getLastname,
                      Account::getMail,
                      Account::getPassword,
                      Account::getLocale,
                      Account::getActive
              )
              .contains(null, null, null, null, null, null, false);
    }

    @Test
    void shouldOnlyDeleteWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.deleteById(1L));

      verify(repository, never()).deleteById(anyLong());
    }
  }
}