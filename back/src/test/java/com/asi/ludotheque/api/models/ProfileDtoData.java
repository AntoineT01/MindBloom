package com.tanguylegoff.templateapp.api.models;

import java.util.List;

import static com.tanguylegoff.templateapp.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tanguylegoff.templateapp.config.Constants.Roles.ROLE_NORMAL;

public final class ProfileDtoData {

  private ProfileDtoData() {

  }

  public static ProfileDto getAdmin() {
    return ProfileDto.builder()
            .id(1L)
            .label(ROLE_ADMINISTRATOR)
            .build();
  }

  public static ProfileDto getNormal() {
    return ProfileDto.builder()
            .id(2L)
            .label(ROLE_NORMAL)
            .build();
  }

  public static List<ProfileDto> getList() {
    return List.of(getAdmin(), getNormal());
  }
}
