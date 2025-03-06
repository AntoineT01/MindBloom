package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.business.EmailService;
import com.tux.mindbloom.config.exceptions.TechnicalException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import static com.tux.mindbloom.config.mail.TemplateConfig.EMAIL_TEMPLATE_ENCODING;
import static com.tux.mindbloom.config.mail.TemplateConfig.EMAIL_TEMPLATE_ROOT_FOLDER;

/**
 * implementation of {@link EmailService}
 */
@Component
public class EmailServiceImpl implements EmailService {

  /**
   * The mail sender
   */
  public static final String MAIL_SENDER = "no-reply@mindbloom.fr";

  /**
   * The morse logo cid to replace in the thymeleaf templates
   */
  public static final String MORSE_LOGO_CID = "logo-ludotheque";

  /**
   * The PNG mime type
   */
  private static final String PNG_MIMETYPE = "image/png";

  /**
   * the javax mail sender service
   */
  private final JavaMailSender emailSender;

  /**
   * the thymeleaf template processor
   */
  private final SpringTemplateEngine templateEngine;

  /**
   * The morse logo as a resource
   */
  private final ClassPathResource morseLogoResource;

  /**
   * Injection constructor
   *
   * @param emailSender    the javax mail sender service
   * @param templateEngine the thymeleaf template processor
   */
  public EmailServiceImpl(JavaMailSender emailSender, SpringTemplateEngine templateEngine) {
    this.emailSender = emailSender;
    this.templateEngine = templateEngine;
    morseLogoResource = new ClassPathResource(EMAIL_TEMPLATE_ROOT_FOLDER + "images/logo-ludotheque.png");
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendSetupPassword(Locale locale, String to, Map<String, Object> fragmentVariables) {

    List<String> orderedContents = prepareContents(locale, "password.signup.html", fragmentVariables);
    String messageSubject = getResourceBundle(locale).getString("password.signup.title");

    sendMail(locale, to, orderedContents, messageSubject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendChangePasswordMail(Locale locale, String to, Map<String, Object> fragmentVariables) {

    List<String> orderedContents = prepareContents(locale, "password.change.html", fragmentVariables);
    String messageSubject = getResourceBundle(locale).getString("password.change.title");

    sendMail(locale, to, orderedContents, messageSubject);
  }

  /**
   * {@inheritDoc}
   */
  public void sendResetPasswordMail(Locale locale, String to, Map<String, Object> fragmentVariables) {
    List<String> orderedContents = prepareContents(locale, "password.reset.html", fragmentVariables);
    String messageSubject = getResourceBundle(locale).getString("password.reset.title");

    sendMail(locale, to, orderedContents, messageSubject);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void sendVerifyMail(Locale locale, String to, Map<String, Object> fragmentVariables) {
    List<String> orderedContents = prepareContents(locale, "mail.verify.html", fragmentVariables);
    String messageSubject = getResourceBundle(locale).getString("mail.verify.title");

    sendMail(locale, to, orderedContents, messageSubject);
  }

  /**
   * Prepares and sends a mail for any in-transfer.
   *
   * @param locale          the mail locale
   * @param to              email to send to
   * @param orderedContents the processed fragments to insert into an in-transfer container
   * @param messageSubject  the mail subject
   */
  private void sendMail(Locale locale, String to, List<String> orderedContents, String messageSubject) {
    try {
      MimeMessage mimeMessage = emailSender.createMimeMessage();
      MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true, EMAIL_TEMPLATE_ENCODING);
      message.setSubject(messageSubject);
      message.setFrom(MAIL_SENDER);
      message.setTo(to);
      message.setText(prepareContainer(locale, orderedContents), true);
      message.addInline(MORSE_LOGO_CID, morseLogoResource, PNG_MIMETYPE);

      emailSender.send(mimeMessage);
    } catch (MessagingException e) {
      throw new TechnicalException("Error sending mail", e);
    }
  }

  /**
   * Prepares a container (the full html page) in which we will insert all prepared contents in order
   *
   * @param locale          the mail locale
   * @param orderedContents the ordered contents to insert in the template
   * @return the processed template
   */
  private String prepareContainer(Locale locale, List<String> orderedContents) {
    Context context = new Context(locale);
    context.setVariable("orderedContents", orderedContents);
    return templateEngine.process("container.html", context);
  }

  /**
   * Prepares a content list from a specific template.
   *
   * @param locale            the mail locale
   * @param fragmentTemplate  the template's name with extension within a thymeleaf known folder
   * @param fragmentVariables the variables required to process the template
   * @return the ordered content list
   */
  private List<String> prepareContents(Locale locale, String fragmentTemplate, Map<String, Object> fragmentVariables) {
    LinkedList<String> orderedContents = new LinkedList<>();

    Context ctx = new Context();
    ctx.setVariables(fragmentVariables);

    ctx.setLocale(locale);
    orderedContents.add(templateEngine.process(fragmentTemplate, ctx));

    return orderedContents;
  }

  /**
   * Will return the required and localized bundle.
   *
   * @param locale a locale to translate the template to
   * @return the required and localized bundle
   */
  private ResourceBundle getResourceBundle(Locale locale) {
    return ResourceBundle.getBundle("notif-i18n", locale);
  }
}
