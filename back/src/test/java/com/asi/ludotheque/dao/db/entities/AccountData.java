package com.tux.mindbloom.dao.db.entities;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_NORMAL;

public final class AccountData {

  private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

  private AccountData() {

  }

  public static Account getAdmin() {
    return Account.builder()
            .id(1L)
            .handle(ROLE_ADMINISTRATOR)
            .firstname(ROLE_ADMINISTRATOR)
            .lastname(ROLE_ADMINISTRATOR)
            .password(ENCODER.encode(ROLE_ADMINISTRATOR))
            .profile(ProfileData.getAdmin())
            .active(true)
            .locale("fr")
            .mail("whatever@example.com")
            .build();
  }

  public static Account getNormal() {
    return Account.builder()
            .id(2L)
            .handle(ROLE_NORMAL)
            .firstname(ROLE_NORMAL)
            .lastname(ROLE_NORMAL)
            .password(ENCODER.encode(ROLE_NORMAL))
            .profile(ProfileData.getAdmin())
            .active(true)
            .locale("fr")
            .mail("whatever@example.com")
            .build();
  }

  public static List<Account> getList() {
    return List.of(getAdmin(), getNormal());
  }
}
