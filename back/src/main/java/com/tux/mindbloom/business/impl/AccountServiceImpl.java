package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.FormLoginDto;
import com.tux.mindbloom.business.AccountRequestService;
import com.tux.mindbloom.business.AccountService;
import com.tux.mindbloom.business.mappers.AccountMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.config.exceptions.MailAlreadyExistsException;
import com.tux.mindbloom.dao.db.AccountRepository;
import com.tux.mindbloom.dao.db.entities.Account;
import com.tux.mindbloom.utils.PasswordUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * implementation of {@link AccountService}
 */
@Service
public class AccountServiceImpl implements AccountService {

  /**
   * Controls for account requests
   */
  private final AccountRequestService accountRequestService;

  /**
   * DB Access for accounts
   */
  private final AccountRepository repository;

  /**
   * Account / AccountDto mapping
   */
  private final AccountMapper mapper;

  /**
   * To encrypt passwords
   */
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Injection constructor
   *
   * @param repository            DB Access for accounts
   * @param mapper                Account / AccountDto mapping
   * @param accountRequestService Controls for account requests
   */
  public AccountServiceImpl(AccountRepository repository, AccountMapper mapper, AccountRequestService accountRequestService) {
    this.repository = repository;
    this.mapper = mapper;
    this.accountRequestService = accountRequestService;
    this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AccountDto> findAll() {
    return mapper.toDtos(repository.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto findById(Long id) {
    return repository
      .findById(id)
      .map(mapper::toDto)
      .orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName(), id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<AccountDto> findAllByMail(String mail) {
    return repository
      .findByMail(mail)
      .stream()
      .map(mapper::toDto)
      .toList();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto create(AccountDto dto) {
    if (repository.findByMail(dto.getMail()).isPresent()) {
      throw new MailAlreadyExistsException(dto.getMail());
    }

    Account account = mapper.toEntity(dto);
    account.setActive(false);
    account.setMail(account.getMail().toLowerCase());
    Account created = repository.save(account);
    AccountDto mapped = mapper.toDto(created);

    // it could have been "accountRequestService.setupPassword(mapped) here,
    // and verification + password would have been done through mail
    accountRequestService.requestMailVerification(mapped);

    return mapped;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto updateById(Long id, AccountDto dto) {
    Account entity = mapper.toEntity(dto);

    AtomicBoolean shouldSendVerificationMail = new AtomicBoolean(false);

    Account found = repository
      .findById(id)
      .map(account -> {
        account.setLastname(entity.getLastname());
        account.setFirstname(entity.getFirstname());
        account.setProfile(entity.getProfile());
        account.setOauthId(entity.getOauthId());
        account.setOauthProvider(entity.getOauthProvider());
        account.setLocale(entity.getLocale());
        if (account.getMail() != null && !account.getMail().equals(dto.getMail())) {
          account.setActive(false);
          shouldSendVerificationMail.set(true);
          account.setMail(dto.getMail().toLowerCase());
        }
        return account;
      })
      .orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName(), id));

    Account updated = repository.save(found);

    if (shouldSendVerificationMail.get()) {
      // Mail address was changed, we should re verify it
      accountRequestService.requestMailVerification(dto);
    }
    return mapper.toDto(updated);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    Account found = repository
      .findById(id)
      .map(account -> {
        // We only remove identification from the account (RGPD), we don't want to delete it
        account.setLastname(null);
        account.setFirstname(null);
        account.setPassword(null);
        account.setOauthProvider(null);
        account.setOauthId(null);
        account.setLocale(null);
        account.setActive(false);

        return account;
      })
      .orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName(), id));

    repository.save(found);
  }

  @Override
  public void logout(HttpServletResponse response) {
    // Invalidate the JWT by removing the authToken cookie
    Cookie cookie = new Cookie("authToken", null);
    cookie.setHttpOnly(true);
    cookie.setSecure(true);
    cookie.setPath("/");
    cookie.setMaxAge(0); // Expire immédiatement
    response.addCookie(cookie);

    // Clear the SecurityContext to remove authentication information
    SecurityContextHolder.clearContext();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updatePassword(Long id, FormLoginDto formLoginDto) {
    Account account = repository
      .findById(id)
      .orElseThrow(() -> new EntityNotFoundException(Account.class.getSimpleName(), id));

    String decryptedPassword = PasswordUtils.decryptPassword(formLoginDto.getPassword());
    account.setPassword(bCryptPasswordEncoder.encode(decryptedPassword));
    repository.save(account);
  }
}
