package com.tanguylegoff.templateapp.business.impl;

import com.tanguylegoff.templateapp.api.models.AccountDto;
import com.tanguylegoff.templateapp.api.models.SignupDto;
import com.tanguylegoff.templateapp.business.AccountRequestService;
import com.tanguylegoff.templateapp.business.AccountService;
import com.tanguylegoff.templateapp.business.SignupService;
import com.tanguylegoff.templateapp.business.mappers.AccountMapper;
import com.tanguylegoff.templateapp.config.exceptions.EntityNotFoundException;
import com.tanguylegoff.templateapp.config.exceptions.MailAlreadyExistsException;
import com.tanguylegoff.templateapp.dao.db.AccountRepository;
import com.tanguylegoff.templateapp.dao.db.ProfileRepository;
import com.tanguylegoff.templateapp.dao.db.entities.Account;
import com.tanguylegoff.templateapp.dao.db.entities.Profile;
import com.tanguylegoff.templateapp.utils.PasswordUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * implementation of {@link AccountService}
 */
@Service
public class SignupServiceImpl implements SignupService {

  /**
   * Controls for account requests
   */
  private final AccountRequestService accountRequestService;

  /**
   * DB Access for accounts
   */
  private final AccountRepository accountRepository;

  /**
   * DB Access for accounts
   */
  private final ProfileRepository profileRepository;

  /**
   * Account / AccountDto mapping
   */
  private final AccountMapper mapper;

  /**
   * Encrypts passwords
   */
  private final BCryptPasswordEncoder bCryptPasswordEncoder;

  /**
   * Injection constructor
   *
   * @param accountRepository     DB Access for accounts
   * @param profileRepository     DB Access for profiles
   * @param mapper                Account / AccountDto mapping
   * @param accountRequestService Controls for account requests
   */
  public SignupServiceImpl(
    AccountRepository accountRepository,
    ProfileRepository profileRepository,
    AccountMapper mapper,
    AccountRequestService accountRequestService
  ) {
    this.accountRepository = accountRepository;
    this.profileRepository = profileRepository;
    this.mapper = mapper;
    this.accountRequestService = accountRequestService;
    this.bCryptPasswordEncoder = new BCryptPasswordEncoder();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public AccountDto signup(SignupDto dto) {
    if (accountRepository.findByMail(dto.getMail()).isPresent()) {
      throw new MailAlreadyExistsException(dto.getMail());
    }

    Profile normalProfile = profileRepository.findByLabel("Normal")
      .orElseThrow(() -> new EntityNotFoundException(Profile.class.getSimpleName(), "Normal"));

    String decryptedPassword = PasswordUtils.decryptPassword(dto.getPassword());

    // Créer l'entité Account avec le mot de passe déchiffré et haché
    Account account = Account.builder()
      .mail(dto.getMail().toLowerCase())
      .locale(dto.getLocale())
      .password(bCryptPasswordEncoder.encode(decryptedPassword))  // Hachage du mot de passe déchiffré
      .firstname(dto.getFirstname())
      .lastname(dto.getLastname())
      .active(false)
      .profile(normalProfile)
      .build();

    Account created = accountRepository.save(account);
    AccountDto mapped = mapper.toDto(created);
    accountRequestService.requestMailVerification(mapped);
    return mapped;
  }
}
