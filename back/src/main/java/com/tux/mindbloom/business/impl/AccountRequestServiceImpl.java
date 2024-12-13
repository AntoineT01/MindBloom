package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.AccountRequestDto;
import com.tux.mindbloom.business.AccountRequestService;
import com.tux.mindbloom.business.EmailService;
import com.tux.mindbloom.business.mappers.AccountMapper;
import com.tux.mindbloom.config.AppConfig;
import com.tux.mindbloom.config.exceptions.BadArgumentException;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.AccountRepository;
import com.tux.mindbloom.dao.db.AccountRequestRepository;
import com.tux.mindbloom.dao.db.entities.Account;
import com.tux.mindbloom.dao.db.entities.AccountRequest;
import com.tux.mindbloom.dao.db.entities.RequestType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

/**
 * implementation of {@link AccountRequestService}
 */
@Service
public class AccountRequestServiceImpl implements AccountRequestService {

  /**
   * Key for the firstname arg in mail template
   */
  public static final String FIRSTNAME = "firstname";

  /**
   * Key for the lastname arg in mail template
   */
  public static final String LASTNAME = "lastname";

  /**
   * Key for the token arg in mail template
   */
  public static final String TOKEN = "token";

  /**
   * Key for the url arg in mail template
   */
  public static final String URL = "url";

  /**
   * DB Access for account requests
   */
  private final AccountRequestRepository repository;

  /**
   * Prepares and sends emails
   */
  private final EmailService emailService;

  /**
   * Account / AccountDto mapping
   */
  private final AccountMapper mapper;

  /**
   * DB Access for accounts
   */
  private final AccountRepository accountRepository;

  /**
   * Encrypts passwords
   */
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * App configuration
   */
  private final AppConfig appConfig;

  /**
   * Injection constructor
   *
   * @param repository        DB Access for account requests
   * @param emailService      Prepares and sends emails
   * @param mapper            Account / AccountDto mapping
   * @param accountRepository DB Access for accounts
   */
  public AccountRequestServiceImpl(
    AccountRequestRepository repository,
    EmailService emailService,
    AccountMapper mapper,
    AccountRepository accountRepository,
    AppConfig appConfig
  ) {
    this.repository = repository;
    this.mapper = mapper;
    this.emailService = emailService;
    this.accountRepository = accountRepository;
    this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
    this.appConfig = appConfig;
  }

  /**
   * Prepares an expiration date in 10 minutes
   *
   * @return the expiration date
   */
  private static Date getExpirationIn10m() {
    Calendar calendar = new GregorianCalendar();
    calendar.add(Calendar.MINUTE, 10);
    return calendar.getTime();
  }

  /**
   * Prepares an expiration date in 1 day
   *
   * @return the expiration date
   */
  private static Date getExpirationIn1d() {
    Calendar calendar = new GregorianCalendar();
    calendar.add(Calendar.DAY_OF_MONTH, 1);
    return calendar.getTime();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requestPasswordInitialSetup(AccountDto dto) {
    AccountRequest request = prepareRequest(dto, getExpirationIn1d(), RequestType.SIGNUP);
    repository.save(request);

    emailService.sendSetupPassword(parseIetfTag(dto.getLocale()), dto.getMail(), Map.of(
      FIRSTNAME, dto.getFirstname(),
      LASTNAME, dto.getLastname(),
      TOKEN, request.getToken()
    ));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requestPasswordModification(AccountDto dto) {
    AccountRequest request = prepareRequest(dto, getExpirationIn10m(), RequestType.CHANGE);
    repository.save(request);

    emailService.sendChangePasswordMail(parseIetfTag(dto.getLocale()), dto.getMail(), Map.of(
      FIRSTNAME, dto.getFirstname(),
      LASTNAME, dto.getLastname(),
      TOKEN, request.getToken()
    ));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requestPasswordReset(AccountDto dto) {
    AccountRequest request = prepareRequest(dto, getExpirationIn10m(), RequestType.RESET);
    repository.save(request);

    emailService.sendResetPasswordMail(parseIetfTag(dto.getLocale()), dto.getMail(), Map.of(
      FIRSTNAME, dto.getFirstname(),
      LASTNAME, dto.getLastname(),
      TOKEN, request.getToken()
    ));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void requestMailVerification(AccountDto dto) {
    AccountRequest request = prepareRequest(dto, getExpirationIn1d(), RequestType.VERIFYMAIL);
    repository.save(request);

    emailService.sendVerifyMail(parseIetfTag(dto.getLocale()), dto.getMail(), Map.of(
      URL, appConfig.getBaseUrl() + appConfig.getAccountVerifyPath(),
      TOKEN, request.getToken()
    ));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void resolveRequest(AccountRequestDto dto) {
    AccountRequest accountRequest = repository.findByToken(dto.getToken())
      .orElseThrow(() -> new EntityNotFoundException(AccountRequestDto.class.getSimpleName(), dto.getToken()));

    // Controls expiration date, must not be passed
    if (accountRequest.getExpirationDate().before(new GregorianCalendar().getTime())) {
      throw new BadArgumentException("Request is expired");
    }

    switch (accountRequest.getRequestType()) {
      case RESET, CHANGE -> handleResetOrChangePasswordRequest(dto, accountRequest);
      case SIGNUP -> handleFinishSignupRequest(dto, accountRequest);
      case VERIFYMAIL -> handleVerifyMailRequest(accountRequest);
    }
  }

  /**
   * A mail verification resolution just sets the account active
   *
   * @param accountRequest the account request
   */
  private void handleVerifyMailRequest(AccountRequest accountRequest) {
    Account account = accountRequest.getAccount();
    account.setActive(true);

    accountRepository.save(account);
  }

  /**
   * A finish signup resolution expects an initial password.
   * It will set the account active at the same time.
   * The password will be encrypted.
   *
   * @param dto            the account request resolution
   * @param accountRequest the account request
   */
  private void handleFinishSignupRequest(AccountRequestDto dto, AccountRequest accountRequest) {
    if (dto.getPassword() == null) {
      throw new BadArgumentException("Missing required password in resolution");
    }

    // We use this call to verify the mail is correct
    Account account = accountRequest.getAccount();
    account.setActive(true);
    account.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

    accountRepository.save(account);
  }

  /**
   * A change or reset resolution expects an initial password.
   * The password will be encrypted.
   * No changes on active status.
   *
   * @param dto            the account request resolution
   * @param accountRequest the account request
   */
  private void handleResetOrChangePasswordRequest(AccountRequestDto dto, AccountRequest accountRequest) {
    if (dto.getPassword() == null) {
      throw new BadArgumentException("Missing required password in resolution");
    }

    // No need to change validity of account, it should be true here
    Account account = accountRequest.getAccount();
    account.setPassword(bCryptPasswordEncoder.encode(dto.getPassword()));

    accountRepository.save(account);
  }

  /**
   * Prepares an account request with a random token and an expiration date in 10 minutes
   *
   * @param dto  the account to set the request for
   * @param type the type of request
   * @return the prepared request
   */
  private AccountRequest prepareRequest(AccountDto dto, Date expirationDate, RequestType type) {
    String randomToken = UUID.randomUUID().toString();

    return AccountRequest.builder()
      .account(mapper.toEntity(dto))
      .token(randomToken)
      .expirationDate(expirationDate)
      .requestType(type)
      .build();
  }

  /**
   * Parses a Locale from an ietf tag ("fr", "en"). beware, you can give anything to this variable but null, and it will invent a new language.
   * Will parse French if the ietf tag is null.
   *
   * @param ietfTag a hopefully valid ietf tag.
   * @return the parsed locale or French
   */
  private Locale parseIetfTag(String ietfTag) {
    if (ietfTag != null) {
      return Locale.forLanguageTag(ietfTag);
    } else {
      return Locale.FRENCH;
    }
  }
}
