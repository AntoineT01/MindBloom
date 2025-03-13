package com.tux.mindbloom.dao.db.entities;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public final class AccountRequestData {

  public static final Date PAST_DATE;
  public static final Date FUTURE_DATE;

  static {
    Calendar cal = new GregorianCalendar();
    cal.add(Calendar.DAY_OF_MONTH, 1);
    FUTURE_DATE = cal.getTime();
    cal.add(Calendar.DAY_OF_MONTH, -2);
    PAST_DATE = cal.getTime();
  }

  private AccountRequestData() {
  }

  public static AccountRequest getVerificationRequest() {
    return AccountRequest.builder()
            .id(1L)
            .token("51e99a58-4c61-4bb2-87aa-29b7c32c531e")
            .expirationDate(FUTURE_DATE)
            .account(AccountData.getNormal())
            .requestType(RequestType.VERIFYMAIL)
            .build();
  }

  public static AccountRequest getSignupRequest() {
    return AccountRequest.builder()
            .id(1L)
            .token("51e99a58-4c61-4bb2-87aa-29b7c32c531e")
            .expirationDate(FUTURE_DATE)
            .account(AccountData.getNormal())
            .requestType(RequestType.SIGNUP)
            .build();
  }

  public static AccountRequest getChangePasswordRequest() {
    return AccountRequest.builder()
            .id(1L)
            .token("51e99a58-4c61-4bb2-87aa-29b7c32c531e")
            .expirationDate(FUTURE_DATE)
            .account(AccountData.getNormal())
            .requestType(RequestType.CHANGE)
            .build();
  }

  public static AccountRequest getResetPasswordRequest() {
    return AccountRequest.builder()
            .id(1L)
            .token("51e99a58-4c61-4bb2-87aa-29b7c32c531e")
            .expirationDate(FUTURE_DATE)
            .account(AccountData.getNormal())
            .requestType(RequestType.RESET)
            .build();
  }
}
