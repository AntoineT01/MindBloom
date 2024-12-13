package com.tanguylegoff.templateapp.config;

public final class Constants {

  private Constants() {
  }

  public static class Profiles {

    public static final String TEST = "test";
    public static final String NOT_TEST = "!test";

    private Profiles() {

    }
  }

  public static final class Authority {

    public static final String ADMINISTRATOR = "hasAuthority('Administrator')";
    public static final String NORMAL = "hasAuthority('Normal')";

    private Authority() {
    }
  }


  public static final class Roles {

    public static final String ROLE_ADMINISTRATOR = "Administrator";
    public static final String ROLE_NORMAL = "Normal";

    private Roles() {
    }
  }

  public static final class Security {
    public static final String SECRET_KEY = "gVkYp3s6v9y$B&E)H@McQfTjWmZq4t7w";
    public static final long AUTHORIZATION_EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String JWT_TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";

    private Security() {
    }
  }

  public static final class Api {
    public static final String LOGIN = "/login";
    public static final String ME = "/me";
    public static final String SOMETHING = "/somethings";
    public static final String ACCOUNTS = "/accounts";
    public static final String SIGNUPS = "/signup";
    public static final String ACCOUNT_REQUESTS = "/account-requests";
    public static final String PROFILES = "/profiles";
    public static final String COUNTRIES = "/countries";
    public static final String PASSWORD = "/password";
    public static final String LOGOUT = "/logout";

    private Api() {
    }
  }
}