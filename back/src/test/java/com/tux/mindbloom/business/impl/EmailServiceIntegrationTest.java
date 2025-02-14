package com.tux.mindbloom.business.impl;

import com.tux.mindbloom.TemplateApplication;
import com.tux.mindbloom.business.EmailService;
import com.icegreen.greenmail.configuration.GreenMailConfiguration;
import com.icegreen.greenmail.junit5.GreenMailExtension;
import com.icegreen.greenmail.store.FolderException;
import com.icegreen.greenmail.util.ServerSetupTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TemplateApplication.class)
class EmailServiceIntegrationTest {
  @RegisterExtension
  final static GreenMailExtension greenMail = new GreenMailExtension(ServerSetupTest.SMTP)
          .withConfiguration(GreenMailConfiguration.aConfig().withUser("ludotheque", "ludotheque"))
          .withPerMethodLifecycle(false);
  @Autowired
  private EmailService service;

  @BeforeEach
  void setup() throws FolderException {
    greenMail.purgeEmailFromAllMailboxes();
  }

  @Test
  void testSendVerifyMail() throws MessagingException {
    service.sendVerifyMail(Locale.FRENCH,
            "whatever@example.com",
            Map.of(
                    "firstname", "The",
                    "Lastname", "Normal",
                    "token", "51e99a58-4c61-4bb2-87aa-29b7c32c531e"
            )
    );
    assertThat(greenMail.getReceivedMessages()).hasSize(1);
    assertThat(Arrays.stream(greenMail.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)).map(Address::toString))
            .hasSize(1)
            .contains("whatever@example.com");
    assertThat(greenMail.getReceivedMessages()[0]
            .getSubject()).isEqualTo("Ludotheque - Vérification de l'adresse mail");
  }

  @Test
  void testSendResetPassword() throws MessagingException {
    service.sendResetPasswordMail(Locale.ENGLISH,
            "whatever@example.com",
            Map.of(
                    "firstname", "The",
                    "Lastname", "Normal",
                    "token", "51e99a58-4c61-4bb2-87aa-29b7c32c531e"
            )
    );
    assertThat(greenMail.getReceivedMessages()).hasSize(1);
    assertThat(Arrays.stream(greenMail.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)).map(Address::toString))
            .hasSize(1)
            .contains("whatever@example.com");
    assertThat(greenMail.getReceivedMessages()[0]
            .getSubject()).isEqualTo("Gaming library - Reset your password");
  }

  @Test
  void testSendChangePassword() throws MessagingException {
    service.sendChangePasswordMail(Locale.FRENCH,
            "whatever@example.com",
            Map.of(
                    "firstname", "The",
                    "Lastname", "Normal",
                    "token", "51e99a58-4c61-4bb2-87aa-29b7c32c531e"
            )
    );
    assertThat(greenMail.getReceivedMessages()).hasSize(1);
    assertThat(Arrays.stream(greenMail.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)).map(Address::toString))
            .hasSize(1)
            .contains("whatever@example.com");
    assertThat(greenMail.getReceivedMessages()[0]
            .getSubject()).isEqualTo("Ludotheque - Modification du mot de passe");
  }

  @Test
  void testSendFinishSignup() throws MessagingException {
    service.sendSetupPassword(Locale.FRENCH,
            "whatever@example.com",
            Map.of(
                    "firstname", "The",
                    "Lastname", "Normal",
                    "token", "51e99a58-4c61-4bb2-87aa-29b7c32c531e"
            )
    );
    assertThat(greenMail.getReceivedMessages()).hasSize(1);
    assertThat(Arrays.stream(greenMail.getReceivedMessages()[0].getRecipients(Message.RecipientType.TO)).map(Address::toString))
            .hasSize(1)
            .contains("whatever@example.com");
    assertThat(greenMail.getReceivedMessages()[0]
            .getSubject()).isEqualTo("Ludotheque - Finalisation de l'inscription");
  }
}
