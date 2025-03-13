package com.tux.mindbloom.dao.db.entities;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_NORMAL;

public final class ProfileData {

  private ProfileData() {

  }

  public static Profile getAdmin() {
    return Profile.builder()
            .id(1L)
            .label(ROLE_ADMINISTRATOR)
            .build();
  }

  public static Profile getNormal() {
    return Profile.builder()
            .id(2L)
            .label(ROLE_NORMAL)
            .build();
  }

  public static List<Profile> getList() {
    return List.of(getAdmin(), getNormal());
  }
}
