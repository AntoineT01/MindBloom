package com.tanguylegoff.templateapp.dao.db;

import com.tanguylegoff.templateapp.dao.db.entities.AccountRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DB Access for account requests
 */
@Repository
public interface AccountRequestRepository extends JpaRepository<AccountRequest, Long> {

  /**
   * Searches for an account request with a given token
   *
   * @param token the required token
   * @return the account request if found, else null
   */
  Optional<AccountRequest> findByToken(String token);
}