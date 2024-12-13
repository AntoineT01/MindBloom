package com.tanguylegoff.templateapp.config.exceptions;

/**
 * Can't act on element, it is missing in database
 */
@SuppressWarnings("java:S110")
public class MailAlreadyExistsException extends BadArgumentException {

  /**
   * Constructs a BadArgumentException with a specific message
   *
   * @param mail the unique email we search for
   */
  public MailAlreadyExistsException(String mail) {
    super("The mail " + mail + " is already taken");
  }
}
