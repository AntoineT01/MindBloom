package com.tux.mindbloom.config.exceptions;

/**
 * Can't act on element, it is missing in database
 */
@SuppressWarnings("java:S110")
public class HandleAlreadyExistsException extends BadArgumentException {

  /**
   * Constructs a BadArgumentException with a specific message
   *
   * @param handle the unique handle we search for
   */
  public HandleAlreadyExistsException(String handle) {
    super("The handle " + handle + " is already taken");
  }
}
