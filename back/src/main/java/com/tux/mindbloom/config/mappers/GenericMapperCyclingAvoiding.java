package com.tux.mindbloom.config.mappers;

import org.mapstruct.Context;

import java.util.List;

/**
 * Defines all standard mapping usages between dtos and entities, but prevents circular relations
 *
 * @param <E> The type of Entity
 * @param <D> The Type of Dto
 */
public interface GenericMapperCyclingAvoiding<E, D> extends PresenceCheckLazy, LazyCollectionMapper {

  /**
   * Maps an Entity to a Dto
   *
   * @param e the entity
   * @return the dto
   */
  @DoIgnore
  default D toDto(E e) {
    return toDto(e, new CycleAvoidingMappingContext());
  }

  /**
   * Maps a Dto to an Entity
   *
   * @param d the dto
   * @return the entity
   */
  @DoIgnore
  default E toEntity(D d) {
    return toEntity(d, new CycleAvoidingMappingContext());
  }

  /**
   * Maps a list of entities to a list of dtos
   *
   * @param e the list of entities
   * @return the list of dtos
   */
  @DoIgnore
  default List<D> toDtos(List<E> e) {
    return toDtos(e, new CycleAvoidingMappingContext());
  }

  /**
   * Maps a list of dtos to a list of entities
   *
   * @param d the list of dtos
   * @return the list of entities
   */
  @DoIgnore
  default List<E> toEntities(List<D> d) {
    return toEntities(d, new CycleAvoidingMappingContext());
  }

  /**
   * Maps an Entity to a Dto
   *
   * @param e                           the entity
   * @param cycleAvoidingMappingContext the circular relation context
   * @return the dto
   */
  D toDto(E e, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  /**
   * Maps a Dto to an Entity
   *
   * @param d                           the dto
   * @param cycleAvoidingMappingContext the circular relation context
   * @return the entity
   */
  E toEntity(D d, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  /**
   * Maps a list of entities to a list of dtos
   *
   * @param e                           the list of entities
   * @param cycleAvoidingMappingContext the circular relation context
   * @return the list of dtos
   */
  List<D> toDtos(List<E> e, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

  /**
   * Maps a list of dtos to a list of entities
   *
   * @param d                           the list of dtos
   * @param cycleAvoidingMappingContext the circular relation context
   * @return the list of entities
   */
  List<E> toEntities(List<D> d, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

}
