package com.tux.mindbloom.util.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

import static com.tux.mindbloom.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tux.mindbloom.config.Constants.Roles.ROLE_NORMAL;

public abstract class RestControllerTestHelper {

  protected static final UserDetails NORMAL_USER = new User("2", ROLE_NORMAL, List.of(new SimpleGrantedAuthority(ROLE_NORMAL)));
  protected static final UserDetails ADMIN_USER = new User("1", ROLE_ADMINISTRATOR, List.of(new SimpleGrantedAuthority(ROLE_ADMINISTRATOR)));

  public static String asJsonString(final Object object) {
    try {
      return new ObjectMapper().writeValueAsString(object);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }
}