package com.tanguylegoff.templateapp.dao.db.entities;

import java.util.List;

public final class SomethingData {

  private SomethingData() {

  }

  public static Something getSome() {
    return Something.builder()
            .id(1L)
            .label("Some")
            .build();
  }

  public static Something getThing() {
    return Something.builder()
            .id(2L)
            .label("Thing")
            .build();
  }

  public static List<Something> getList() {
    return List.of(getSome(), getThing());
  }
}
