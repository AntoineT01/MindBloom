package com.tux.mindbloom.api.models;

import java.util.List;

public final class SomethingDtoData {

  private SomethingDtoData() {

  }

  public static SomethingDto getSome() {
    return SomethingDto.builder()
            .id(1L)
            .label("Some")
            .build();
  }

  public static SomethingDto getThing() {
    return SomethingDto.builder()
            .id(2L)
            .label("Thing")
            .build();
  }

  public static List<SomethingDto> getList() {
    return List.of(getSome(), getThing());
  }
}
