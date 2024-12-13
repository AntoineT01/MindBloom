package com.tanguylegoff.templateapp.config.mappers;

import org.hibernate.Hibernate;
import org.mapstruct.Condition;

import java.util.Collection;

/**
 * Tool for mapping
 */
public interface PresenceCheckLazy {

  /**
   * Condition for mapStruct.
   * All attribute unInitialized (non fetch) is ignored during mapping.
   * This condition allow lazy fetch on attribute by preventing call on getter for lazy fetch nested entities.
   * Collection are ignored with this condition because LazyCollection are map with specific rules
   * defined in {@link LazyCollectionMapper}.
   *
   * @param obj object to check if initialized.
   * @return true if object is a collection or if is initialized (check with {@link Hibernate#initialize(Object)}).
   */
  @Condition
  static boolean isInitialized(Object obj) {
    if (obj instanceof Collection<?>) {
      return true;
    }
    return Hibernate.isInitialized(obj);
  }
}
