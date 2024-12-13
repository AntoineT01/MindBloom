package com.tux.mindbloom.config.mappers;

import org.hibernate.collection.spi.PersistentCollection;
import org.mapstruct.BeforeMapping;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Tool for mapping
 */
public interface LazyCollectionMapper {
  /**
   * Replace an uninitialized (lazy fetch) set by an empty set for mapstruct mapping
   *
   * @param c Collection to check
   * @return empty set if collection is not initialized, null otherwise. when mapstruct receive null, it uses the original value instead
   */
  @BeforeMapping
  // We actually want to return null here in a specific case.
  // We are dealing with lazy loading
  @SuppressWarnings("java:S1168")
  default <T> Set<T> lazyLoadingSet(Collection<?> c) {
    if (isUninitialized(c)) {
      return Collections.emptySet();
    }
    return null;
  }

  /**
   * Replace an uninitialized (lazy fetch) list by an empty list for mapstruct mapping
   *
   * @param c Collection to check
   * @return empty list if collection is not initialized, null otherwise. when mapstruct receive null, it uses the original value instead
   */
  @BeforeMapping
  // We actually want to return null here in a specific case.
  // We are dealing with lazy loading
  @SuppressWarnings("java:S1168")
  default <T> List<T> lazyLoadingList(Collection<?> c) {
    if (isUninitialized(c)) {
      return Collections.emptyList();
    }
    return null;
  }

  private boolean isUninitialized(Object c) {
    if (!(c instanceof PersistentCollection<?> pc)) {
      return false;
    }

    return !pc.wasInitialized();
  }
}
