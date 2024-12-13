package com.tanguylegoff.templateapp.api.models;

public final class AccountRequestDtoData {

  private AccountRequestDtoData() {

  }

  public static AccountRequestDto getToken() {
    return AccountRequestDto.builder()
            .token("51e99a58-4c61-4bb2-87aa-29b7c32c531e")
            .password("ee330f2e66ac10ecf672491ca03201e8d3142625d3ee6059f19ca157cac58102ffa827ee41e56d63e9c4ca6ecf625b0adeae4f55ffb8a113001a8afa6fd9ea1f")
            .build();
  }
}
