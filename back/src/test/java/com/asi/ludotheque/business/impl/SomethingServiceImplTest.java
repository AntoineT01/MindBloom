package com.tanguylegoff.templateapp.business.impl;

import com.tanguylegoff.templateapp.api.models.SomethingDto;
import com.tanguylegoff.templateapp.api.models.SomethingDtoData;
import com.tanguylegoff.templateapp.business.SomethingService;
import com.tanguylegoff.templateapp.business.mappers.SomethingMapper;
import com.tanguylegoff.templateapp.config.exceptions.EntityNotFoundException;
import com.tanguylegoff.templateapp.dao.db.SomethingRepository;
import com.tanguylegoff.templateapp.dao.db.entities.Something;
import com.tanguylegoff.templateapp.dao.db.entities.SomethingData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SomethingServiceImplTest {

  private SomethingService service;

  @Mock
  private SomethingRepository repository;

  private Something something;

  private SomethingDto somethingDto;

  @BeforeEach
  public void setup() {
    service = new SomethingServiceImpl(repository, Mappers.getMapper(SomethingMapper.class));

    something = SomethingData.getSome();
    somethingDto = SomethingDtoData.getSome();
  }

  @Nested
  class FindById {
    @Test
    void ShouldFindById() {
      when(repository.findById(anyLong())).thenReturn(Optional.of(something));

      SomethingDto result = service.findById(1L);

      assertThat(result).isEqualTo(somethingDto);
      verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void shouldOnlyFindWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.findById(1L));
    }
  }

  @Nested
  class FindAll {
    @Test
    void ShouldFindAll() {
      when(repository.findAll()).thenReturn(SomethingData.getList());

      List<SomethingDto> result = service.findAll();

      assertThat(result).isEqualTo(SomethingDtoData.getList());
      verify(repository, times(1)).findAll();
    }
  }

  @Nested
  class Create {
    @Test
    void ShouldCreate() {
      when(repository.save(any(Something.class))).thenReturn(something);

      SomethingDto result = service.create(somethingDto);

      assertThat(result).isEqualTo(somethingDto);
      verify(repository, times(1)).save(any(Something.class));
    }
  }

  @Nested
  class UpdateById {

    @Test
    void ShouldUpdateById() {
      when(repository.findById(anyLong())).thenReturn(Optional.of(something));
      when(repository.save(any(Something.class))).thenReturn(something);

      SomethingDto result = service.updateById(1L, somethingDto);

      assertThat(result).isEqualTo(somethingDto);
      verify(repository, times(1)).save(any(Something.class));
    }

    @Test
    void shouldOnlyUpdateWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.updateById(1L, somethingDto));

      verify(repository, never()).save(any(Something.class));
    }
  }

  @Nested
  class Delete {
    @Test
    void shouldDeleteById() {
      when(repository.findById(1L)).thenReturn(Optional.of(something));

      service.deleteById(1L);

      verify(repository, times(1)).deleteById(1L);
    }

    @Test
    void shouldOnlyDeleteWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.deleteById(1L));

      verify(repository, never()).deleteById(anyLong());
    }
  }
}