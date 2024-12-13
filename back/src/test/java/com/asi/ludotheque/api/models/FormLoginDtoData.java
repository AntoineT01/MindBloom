package com.tux.mindbloom.api.models;

import static com.tux.mindbloom.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_NORMAL;

public final class FormLoginDtoData {

  private FormLoginDtoData() {

  }

  public static FormLoginDto getNormal() {
    return FormLoginDto.builder()
            .handle(ROLE_NORMAL)
            .password(ROLE_NORMAL)
            .build();
  }

  public static FormLoginDto getAdmin() {
    return FormLoginDto.builder()
            .handle(ROLE_ADMINISTRATOR)
            .password(ROLE_ADMINISTRATOR)
            .build();
  }
}
