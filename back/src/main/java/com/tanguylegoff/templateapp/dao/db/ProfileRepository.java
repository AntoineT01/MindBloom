package com.tanguylegoff.templateapp.dao.db;

import com.tanguylegoff.templateapp.dao.db.entities.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * DB Access for profiles
 */
@Repository
public interface ProfileRepository extends JpaRepository<Profile, Long> {

  /**
   * Searches for a profile with a given label
   *
   * @param label the required label
   * @return the profile if found, else null
   */
  Optional<Profile> findByLabel(String label);

}