package com.tanguylegoff.templateapp.config.exceptions;

/**
 * Client input was incorrect
 */
public class BadArgumentException extends FunctionalException {

  /**
   * Constructs a new runtime exception with the specified detail message.
   * The cause is not initialized, and may subsequently be initialized by a
   * call to {@link #initCause}.
   *
   * @param message the detail message. The detail message is saved for
   *                later retrieval by the {@link #getMessage()} method.
   */

  public BadArgumentException(String message) {
    super(message);
  }
}
