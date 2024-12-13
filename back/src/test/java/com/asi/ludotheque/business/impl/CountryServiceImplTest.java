package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.CountryDto;
import com.tux.mindbloom.api.models.CountryDtoData;
import com.tux.mindbloom.business.CountriesService;
import com.tux.mindbloom.business.mappers.CountriesMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.entities.Country;
import com.tux.mindbloom.dao.db.entities.CountryData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class CountryServiceImplTest {

  private CountriesService service;

  private List<CountryDto> countriesDto;

  @BeforeEach
  public void setup() {
    service = new CountriesServiceImpl(Mappers.getMapper(CountriesMapper.class));
    CountriesServiceImpl.COUNTRIES_DATA = CountryData.getList();
    countriesDto = CountryDtoData.getList();
  }

  @Nested
  class FindAll {
    @Test
    void ShouldFindAll() {
      List<CountryDto> result = service.findAll(null);

      assertThat(result).isEqualTo(CountryDtoData.getList());
    }

    @Test
    void ShouldFindAllWithMaxId() {
      List<CountryDto> result = service.findAll(3);

      assertThat(result).isEqualTo(List.of(countriesDto.get(0), countriesDto.get(1), countriesDto.get(2)));
    }

    @Test
    void ShouldFindAllWithMaxIdGreaterThanMaxId() {
      List<CountryDto> result = service.findAll(10);

      assertThat(result).isEqualTo(CountryDtoData.getList());
    }
  }

  @Nested
  class FindById {
    @Test
    void ShouldFindById() {
      CountryDto result = service.findById(1L);

      assertThat(result).isEqualTo(countriesDto.getFirst());
    }

    @Test
    void ShouldOnlyFindWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.findById(10L));
    }

    @Test
    void ShouldHavePositiveId() {
      assertThrows(EntityNotFoundException.class, () -> service.findById(-1L));
    }
  }

  @Nested
  class Create {
    @Test
    void ShouldCreate() {
      CountryDto countryDto = countriesDto.getFirst();
      countryDto.setId(null);
      CountryDto result = service.create(countryDto);
      countryDto.setId(7L);

      assertThat(result).isEqualTo(countryDto);
    }
  }

  @Nested
  class UpdateById {
    @Test
    void ShouldUpdateById() {
      CountryDto countryDto = countriesDto.getFirst();
      countryDto.setLabel("Italy");
      CountryDto result = service.updateById(1L, countriesDto.getFirst());

      assertThat(result).isEqualTo(countriesDto.getFirst());
    }

    @Test
    void ShouldOnlyUpdateWhenEntityExists() {
      CountryDto countryDto = countriesDto.getFirst();
      assertThrows(EntityNotFoundException.class, () -> service.updateById(10L, countryDto));
    }
  }

  @Nested
  class Delete {
    @Test
    void ShouldDeleteById() {
      service.deleteById(1L);
      assertThat(CountriesServiceImpl.COUNTRIES_DATA).extracting(Country::getId).doesNotContain(1L);
    }

    @Test
    void ShouldOnlyDeleteWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.deleteById(100L));
    }
  }
}