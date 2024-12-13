package com.tux.mindbloom.config.database;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import static com.tux.mindbloom.config.Constants.Profiles.NOT_TEST;
import static com.tux.mindbloom.config.Constants.Profiles.TEST;

/**
 * Configures JPA
 */
@Configuration
@EnableTransactionManagement
@EnableJpaAuditing
@EnableJpaRepositories("com.tux.mindbloom.dao.db")
@EntityScan("com.tux.mindbloom.dao.db.entities")
public class ConfigDatabase {

  /**
   * Registers auditing feature for production
   *
   * @return the auditing feature
   */
  @Bean
  @Profile(NOT_TEST)
  AuditorAware<Long> auditorProvider() {
    return new UsernameAuditorAware();
  }

  /**
   * Registers auditing feature for tests.
   * (Maybe set it up in test packages)
   *
   * @return the auditing feature
   */
  @Bean
  @Profile(TEST)
  AuditorAware<Long> auditorProviderTest() {
    return new AuditorAwareTest();
  }
}
