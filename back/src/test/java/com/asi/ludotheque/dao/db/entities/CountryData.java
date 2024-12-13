package com.tux.mindbloom.dao.db.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CountryData {

  public static List<Country> getList() {
    return new ArrayList<>(Arrays.asList(
            new Country(1L, "France"),
            new Country(2L, "Germany"),
            new Country(3L, "Spain"),
            new Country(4L, "Italy"),
            new Country(5L, "United Kingdom"),
            new Country(6L, "United States")
    ));
  }

  public static Country getNew() {
    return new Country(null, "Canada");
  }
}
