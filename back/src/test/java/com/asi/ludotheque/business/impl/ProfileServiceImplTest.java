package com.tanguylegoff.templateapp.business.impl;


import com.tanguylegoff.templateapp.api.models.ProfileDto;
import com.tanguylegoff.templateapp.api.models.ProfileDtoData;
import com.tanguylegoff.templateapp.business.ProfileService;
import com.tanguylegoff.templateapp.business.mappers.ProfileMapper;
import com.tanguylegoff.templateapp.config.exceptions.EntityNotFoundException;
import com.tanguylegoff.templateapp.dao.db.ProfileRepository;
import com.tanguylegoff.templateapp.dao.db.entities.ProfileData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.tanguylegoff.templateapp.config.Constants.Roles.ROLE_NORMAL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

  private ProfileService service;

  @Mock
  private ProfileRepository repository;

  @BeforeEach
  public void setup() {
    service = new ProfileServiceImpl(repository, Mappers.getMapper(ProfileMapper.class));
  }

  @Nested
  class FindByLabel {
    @Test
    void ShouldFindByLabel() {
      when(repository.findByLabel(ROLE_NORMAL)).thenReturn(Optional.of(ProfileData.getNormal()));

      ProfileDto result = service.findByLabel(ROLE_NORMAL);

      assertThat(result).isEqualTo(ProfileDtoData.getNormal());
      verify(repository, times(1)).findByLabel(ROLE_NORMAL);
    }

    @Test
    void shouldOnlyFindWhenEntityExists() {
      assertThrows(EntityNotFoundException.class, () -> service.findByLabel(ROLE_NORMAL));
    }
  }

  @Nested
  class FindAll {
    @Test
    void ShouldFindAll() {
      when(repository.findAll()).thenReturn(ProfileData.getList());

      List<ProfileDto> result = service.findAll();

      assertThat(result).isEqualTo(ProfileDtoData.getList());
      verify(repository, times(1)).findAll();
    }
  }
}