package com.tanguylegoff.templateapp.config.mappers;

import org.mapstruct.BeforeMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.TargetType;

import java.util.IdentityHashMap;
import java.util.Map;

/**
 * An implementation to track cycles in graphs to be used as {@link org.mapstruct.Context} parameter.
 */
public class CycleAvoidingMappingContext {

  /**
   * Stores occurrences of already mapped objects
   */
  private final Map<Object, Object> knownInstances;

  /**
   * Default constructor
   */
  public CycleAvoidingMappingContext() {
    knownInstances = new IdentityHashMap<>();
  }

  /**
   * Gets an instance from cache if it is already mapped.
   *
   * @param source     given source
   * @param targetType given target type.
   * @return instances from cache
   */
  @BeforeMapping
  public <T> T getMappedInstance(Object source, @TargetType Class<T> targetType) {
    return targetType.cast(knownInstances.get(source));
  }

  /**
   * Puts an instance into the cache, so it can be remembered to avoid endless mapping.
   *
   * @param source given source
   * @param target given target
   */
  @BeforeMapping
  public void storeMappedInstance(Object source, @MappingTarget Object target) {
    knownInstances.put(source, target);
  }
}
