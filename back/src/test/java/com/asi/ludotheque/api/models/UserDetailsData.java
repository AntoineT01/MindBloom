package com.tanguylegoff.templateapp.api.models;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

import static com.tanguylegoff.templateapp.config.Constants.Roles.ROLE_ADMINISTRATOR;
import static com.tanguylegoff.templateapp.config.Constants.Roles.ROLE_NORMAL;

public final class UserDetailsData {
  private final static BCryptPasswordEncoder ENCODER = new BCryptPasswordEncoder();

  private UserDetailsData() {

  }

  public static UserDetails getNormal() {
    return new User(ROLE_NORMAL, ENCODER.encode(ROLE_NORMAL), List.of(new SimpleGrantedAuthority(ROLE_NORMAL)));
  }

  public static UserDetails getAdmin() {
    return new User(ROLE_ADMINISTRATOR, ENCODER.encode(ROLE_ADMINISTRATOR), List.of(new SimpleGrantedAuthority(ROLE_ADMINISTRATOR)));
  }
}
