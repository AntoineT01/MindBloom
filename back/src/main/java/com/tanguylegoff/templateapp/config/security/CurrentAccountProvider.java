package com.tanguylegoff.templateapp.config.security;

/**
 * Util class that wrap context for usage in the whole application
 */
public interface CurrentAccountProvider {

  /**
   * Return the connected user
   *
   * @return user with role if connected otherwise <code>null</code>
   */
  Long get();

}