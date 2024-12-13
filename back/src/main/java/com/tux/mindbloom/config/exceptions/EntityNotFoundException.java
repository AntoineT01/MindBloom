package com.tux.mindbloom.config.exceptions;

/**
 * Can't act on element, it is missing in database
 */
@SuppressWarnings("java:S110")
public class EntityNotFoundException extends BadArgumentException {

  /**
   * Constructs a BadArgumentException with a specific message
   *
   * @param entity the kind of entity we are searching for in the database
   * @param id     the specific id we search for
   */
  public EntityNotFoundException(String entity, Long id) {
    super("The " + entity + " with id " + id + " does not exist");
  }

  /**
   * Constructs a BadArgumentException with a specific message
   *
   * @param entity the kind of entity we are searching for in the database
   * @param value  the unique value we search for
   */
  public EntityNotFoundException(String entity, String value) {
    super("The " + entity + " \"" + value + "\" does not exist");
  }
}
