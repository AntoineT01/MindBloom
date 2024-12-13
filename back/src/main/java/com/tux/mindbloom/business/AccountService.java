package com.tux.mindbloom.business;

import com.tux.mindbloom.api.models.AccountDto;
import com.tux.mindbloom.api.models.FormLoginDto;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

/**
 * Controls for accounts
 */
@Transactional
@Validated
public interface AccountService {

  /**
   * Returns all existing accounts
   *
   * @return all existing accounts
   */
  List<AccountDto> findAll();

  /**
   * Returns the account with a specific id
   *
   * @param id the required id, must be set
   * @return the found account
   * @throws EntityNotFoundException if the account couldn't be found
   */
  AccountDto findById(@NotNull Long id);

  /**
   * Returns all the account with a specific mail, else returns an empty collection
   *
   * @param mail the required mail, must be set
   * @return the found accounts
   */
  List<AccountDto> findAllByMail(@NotNull String mail);

  /**
   * Creates an account
   *
   * @param dto a valid account to create, must be set
   * @return the created account
   */
  AccountDto create(@Valid @NotNull AccountDto dto);

  /**
   * Updates an account with a specific id
   *
   * @param id  the required id, must be set
   * @param dto a valid something to update, must be set
   * @return the updated account
   * @throws EntityNotFoundException if the account couldn't be found
   */
  AccountDto updateById(@NotNull Long id, @Valid @NotNull AccountDto dto);

  /**
   * Deletes an account with a specific id.
   * Accounts are not really deleted, we will keep them linked to past reviews
   *
   * @param id the required id, must be set
   * @throws EntityNotFoundException if the account couldn't be found
   */
  void deleteById(@NotNull Long id);

  /**
   * Logs out the account by invalidating the authentication token.
   *
   * @param response the HTTP response to modify for logout
   */
  void logout(HttpServletResponse response);

  void updatePassword(@NotNull Long id, @Valid @NotNull FormLoginDto formLoginDto);
}
