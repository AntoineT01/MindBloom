package com.tanguylegoff.templateapp.config.exceptions;

import org.springframework.security.core.AuthenticationException;

/**
 * A wrong token was given in a header
 */
public class JwtAuthenticationException extends AuthenticationException {

  /**
   * Constructs an exception with a message and a cause
   *
   * @param msg the message
   * @param t   the cause
   */
  public JwtAuthenticationException(String msg, Throwable t) {
    super(msg, t);
  }
}