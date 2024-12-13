package com.tanguylegoff.templateapp.config.mappers;

import java.util.List;

/**
 * Defines all standard mapping usages between dtos and entities
 *
 * @param <E> The type of Entity
 * @param <D> The Type of Dto
 */
public interface GenericMapper<E, D> extends PresenceCheckLazy, LazyCollectionMapper {

  /**
   * Maps an Entity to a Dto
   *
   * @param e the entity
   * @return the dto
   */
  D toDto(E e);

  /**
   * Maps a Dto to an Entity
   *
   * @param d the dto
   * @return the entity
   */
  E toEntity(D d);

  /**
   * Maps a list of entities to a list of dtos
   *
   * @param e the list of entities
   * @return the list of dtos
   */
  List<D> toDtos(List<E> e);

  /**
   * Maps a list of dtos to a list of entities
   *
   * @param d the list of dtos
   * @return the list of entities
   */
  List<E> toEntities(List<D> d);

}
