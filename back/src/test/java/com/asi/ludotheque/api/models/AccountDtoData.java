package com.tux.mindbloom.api.models;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_NORMAL;

public final class AccountDtoData {

  private AccountDtoData() {

  }

  public static AccountDto getAdmin() {
    return AccountDto.builder()
            .id(1L)
            .handle(ROLE_ADMINISTRATOR)
            .firstname(ROLE_ADMINISTRATOR)
            .lastname(ROLE_ADMINISTRATOR)
            .profile(ProfileDtoData.getAdmin())
            .locale("fr")
            .mail("whatever@example.com")
            .build();
  }

  public static AccountDto getNormal() {
    return AccountDto.builder()
            .id(2L)
            .handle(ROLE_NORMAL)
            .firstname(ROLE_NORMAL)
            .lastname(ROLE_NORMAL)
            .profile(ProfileDtoData.getAdmin())
            .locale("fr")
            .mail("whatever@example.com")
            .build();
  }

  public static List<AccountDto> getList() {
    return List.of(getAdmin(), getNormal());
  }
}
