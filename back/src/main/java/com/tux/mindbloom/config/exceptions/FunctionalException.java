package com.tux.mindbloom.config.exceptions;

import java.io.Serial;

/**
 * A functional Exception as defined in this Api
 */
public class FunctionalException extends RuntimeException {

  /**
   * Serialization UID
   */
  @Serial
  private static final long serialVersionUID = 3284093311174321372L;

  /**
   * Constructor with message
   *
   * @param message le message
   */
  public FunctionalException(String message) {
    super(message);
  }

  /**
   * Constructor with message and cause
   *
   * @param message the message
   * @param cause   the cause
   */
  public FunctionalException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with  cause
   *
   * @param cause the cause
   */
  public FunctionalException(Throwable cause) {
    super(cause);
  }
}
