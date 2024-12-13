package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.api.models.SomethingDto;
import com.tux.mindbloom.business.SomethingService;
import com.tux.mindbloom.business.mappers.SomethingMapper;
import com.tux.mindbloom.config.exceptions.EntityNotFoundException;
import com.tux.mindbloom.dao.db.SomethingRepository;
import com.tux.mindbloom.dao.db.entities.Something;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * implementation of {@link SomethingService}
 */
@Service
public class SomethingServiceImpl implements SomethingService {

  /**
   * DB Access for something
   */
  private final SomethingRepository repository;

  /**
   * Something / SomethingDto mapping
   */
  private final SomethingMapper mapper;

  /**
   * Injection constructor
   *
   * @param repository DB Access for something
   * @param mapper     Something / SomethingDto mapping
   */
  public SomethingServiceImpl(SomethingRepository repository, SomethingMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<SomethingDto> findAll() {
    return mapper.toDtos(repository.findAll());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SomethingDto findById(Long id) {
    return repository
            .findById(id)
            .map(mapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException(Something.class.getSimpleName(), id));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SomethingDto create(SomethingDto dto) {
    Something entity = mapper.toEntity(dto);
    Something created = repository.save(entity);
    return mapper.toDto(created);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public SomethingDto updateById(Long id, SomethingDto dto) {
    Something entity = mapper.toEntity(dto);

    Something found = repository.findById(dto.getId())
            .map(something -> {
              // Only update specific properties instead of overriding audit model
              something.setLabel(entity.getLabel());

              return something;
            })
            .orElseThrow(() -> new EntityNotFoundException(Something.class.getSimpleName(), dto.getId()));

    Something updated = repository.save(found);
    return mapper.toDto(updated);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void deleteById(Long id) {
    if (repository.findById(id).isEmpty()) {
      throw new EntityNotFoundException(Something.class.getSimpleName(), id);
    }

    repository.deleteById(id);
  }
}
