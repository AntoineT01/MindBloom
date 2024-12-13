package com.tanguylegoff.templateapp.api.models;

public final class SignupDtoData {

  private SignupDtoData() {

  }

  public static SignupDto getAccount() {
    return SignupDto.builder()
            .handle("normal")
            .firstname("The")
            .lastname("Normal")
            .locale("fr")
            .password("ee330f2e66ac10ecf672491ca03201e8d3142625d3ee6059f19ca157cac58102ffa827ee41e56d63e9c4ca6ecf625b0adeae4f55ffb8a113001a8afa6fd9ea1f")
            .mail("whatever@example.com")
            .build();
  }
}
