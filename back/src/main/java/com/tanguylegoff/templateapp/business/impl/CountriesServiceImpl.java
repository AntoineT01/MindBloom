package com.tanguylegoff.templateapp.business.impl;

import com.tanguylegoff.templateapp.api.AbstractRestController;
import com.tanguylegoff.templateapp.api.models.CountryDto;
import com.tanguylegoff.templateapp.business.CountriesService;
import com.tanguylegoff.templateapp.business.mappers.CountriesMapper;
import com.tanguylegoff.templateapp.config.exceptions.EntityNotFoundException;
import com.tanguylegoff.templateapp.dao.db.entities.Country;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CountriesServiceImpl extends AbstractRestController implements CountriesService {

  public static List<Country> COUNTRIES_DATA = new ArrayList<>();

  static {
    COUNTRIES_DATA.add(new Country(1L, "France"));
    COUNTRIES_DATA.add(new Country(2L, "Germany"));
    COUNTRIES_DATA.add(new Country(3L, "Spain"));
    COUNTRIES_DATA.add(new Country(4L, "Italy"));
    COUNTRIES_DATA.add(new Country(5L, "United Kingdom"));
    COUNTRIES_DATA.add(new Country(6L, "United States"));
  }

  private final CountriesMapper mapper;

  public CountriesServiceImpl(CountriesMapper mapper) {
    this.mapper = mapper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<CountryDto> findAll(Integer maxId) {
    // Check if there is a parameter to filter the list
    // If there is a parameter, filter the list
    return mapper.toDtos(maxId != null ?
            COUNTRIES_DATA
                    .stream()
                    .filter(country -> country.getId() <= maxId)
                    .toList()
            : COUNTRIES_DATA);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CountryDto findById(Long id) {
    return mapper.toDto(COUNTRIES_DATA
            .stream()
            .filter(countryFilter -> countryFilter.getId().equals(id))
            .findFirst()
            .orElseThrow(() -> new EntityNotFoundException("Country not found", id.toString())));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CountryDto create(CountryDto countryDto) {
    Country country = mapper.toEntity(countryDto);
    // Generate an id for the new country
    country.setId(COUNTRIES_DATA
            .stream()
            .map(Country::getId)
            .max(Long::compareTo)
            .orElse(0L) + 1);

    COUNTRIES_DATA.add(country);
    return mapper.toDto(country);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CountryDto updateById(Long id, CountryDto countryDto) {
    Country country = mapper.toEntity(countryDto);
    Country countryToUpdate = mapper.toEntity(this.findById(id));
    countryToUpdate.setLabel(country.getLabel());
    return mapper.toDto(countryToUpdate);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    Country country = mapper.toEntity(this.findById(id));
    COUNTRIES_DATA.remove(country);
  }
}
