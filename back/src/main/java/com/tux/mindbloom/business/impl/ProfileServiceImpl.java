package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.ProfileDto;
import com.tux.mindbloom.business.ProfileService;
import com.tux.mindbloom.business.mappers.ProfileMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.ProfileRepository;
import com.tux.mindbloom.dao.db.entities.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * implementation of {@link ProfileService}
 */
@Service
public class ProfileServiceImpl implements ProfileService {

  /**
   * DB Access for profiles
   */
  private final ProfileRepository repository;

  /**
   * Profile / ProfileDto mapping
   */
  private final ProfileMapper mapper;

  /**
   * Injection constructor
   *
   * @param repository DB Access for profiles
   * @param mapper     Profile / ProfileDto mapping
   */
  public ProfileServiceImpl(ProfileRepository repository, ProfileMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<ProfileDto> findAll() {
    return mapper.toDtos(repository.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ProfileDto findByLabel(String label) {
    return repository.findByLabel(label)
            .map(mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException(Profile.class.getSimpleName(), label));
  }
}
