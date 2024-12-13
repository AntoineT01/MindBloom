package com.tanguylegoff.templateapp.business;

import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.util.Locale;
import java.util.Map;

/**
 * Prepares and sends emails
 */
@Validated
public interface EmailService {

  /**
   * Prepares and sends a mail to set up password for the first time
   *
   * @param locale            a locale to translate the template to
   * @param to                email to send to
   * @param fragmentVariables the fragment variables required to process the template
   */
  void sendSetupPassword(
          @NotNull Locale locale,
          @NotNull String to,
          @NotNull Map<String, Object> fragmentVariables
  );

  /**
   * Prepares and sends a mail to change the account's password
   *
   * @param locale            a locale to translate the template to
   * @param to                email to send to
   * @param fragmentVariables the fragment variables required to process the template
   */
  void sendChangePasswordMail(
          @NotNull Locale locale,
          @NotNull String to,
          @NotNull Map<String, Object> fragmentVariables
  );

  /**
   * Prepares and sends a mail to reset the account's password
   *
   * @param locale            a locale to translate the template to
   * @param to                email to send to
   * @param fragmentVariables the fragment variables required to process the template
   */
  void sendResetPasswordMail(
          @NotNull Locale locale,
          @NotNull String to,
          @NotNull Map<String, Object> fragmentVariables
  );

  /**
   * Prepares and sends a mail to verify the account's mail
   *
   * @param locale            a locale to translate the template to
   * @param to                email to send to
   * @param fragmentVariables the fragment variables required to process the template
   */
  void sendVerifyMail(
          @NotNull Locale locale,
          @NotNull String to,
          @NotNull Map<String, Object> fragmentVariables
  );
}
