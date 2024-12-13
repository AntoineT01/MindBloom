package com.tanguylegoff.templateapp.api.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class CountryDtoData {

  public static List<CountryDto> getList() {
    return new ArrayList<>(Arrays.asList(
            new CountryDto(1L, "France"),
            new CountryDto(2L, "Germany"),
            new CountryDto(3L, "Spain"),
            new CountryDto(4L, "Italy"),
            new CountryDto(5L, "United Kingdom"),
            new CountryDto(6L, "United States")
    ));
  }

  public static CountryDto getNew() {
    return new CountryDto(null, "Canada");
  }
}
