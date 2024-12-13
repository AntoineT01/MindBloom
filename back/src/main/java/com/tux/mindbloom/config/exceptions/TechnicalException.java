package com.tux.mindbloom.config.exceptions;

import java.io.Serial;

/**
 * A technical exception as defined in this Api
 */
public class TechnicalException extends RuntimeException {

  /**
   * Serialization UID
   */
  @Serial
  private static final long serialVersionUID = -8076070824043714307L;

  /**
   * Constructor with message
   *
   * @param message le message
   */
  public TechnicalException(String message) {
    super(message);
  }

  /**
   * Constructor with message and cause
   *
   * @param message the message
   * @param cause   the cause
   */
  public TechnicalException(String message, Throwable cause) {
    super(message, cause);
  }

  /**
   * Constructor with  cause
   *
   * @param cause the cause
   */
  public TechnicalException(Throwable cause) {
    super(cause);
  }
}
