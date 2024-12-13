package com.tanguylegoff.templateapp.dao.db;

import com.tanguylegoff.templateapp.dao.db.entities.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DB Access for accounts
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
  /**
   * Searches for all account with a given mail
   *
   * @param mail the required handle
   * @return the accounts if found, else an empty collection
   */
  Optional<Account> findByMail(String mail);
}