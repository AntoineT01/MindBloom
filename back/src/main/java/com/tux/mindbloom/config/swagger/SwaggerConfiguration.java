package com.tux.mindbloom.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

/**
 * Configuration du swagger
 */
@Slf4j
@Configuration
public class SwaggerConfiguration {

  public static final String SWAGGER_MD = "swagger.md";
  /**
   * La version de l'app
   */
  private final String version;

  /**
   * La version de l'app
   */
  private final String activeProfiles;

  /**
   * Constructeur par injection
   *
   * @param version        la version de l'app
   * @param activeProfiles the active profiles
   */
  public SwaggerConfiguration(@Value("${info.build.version}") String version,
                              @Value("${spring.profiles.active}") String activeProfiles) {
    this.version = version;
    this.activeProfiles = activeProfiles;
  }

  /**
   * Openapi settings
   *
   * @return la doc openapi
   */
  @Bean
  public OpenAPI openApi() {
    Info info = new Info().title("MindBloom [" + activeProfiles + "]").version(version);

    String description = getMarkdownDescription();
    if (description != null) {
      info.description(description);
    }

    Contact contact = new Contact();
    contact.setName("PieTux");

    return new OpenAPI()
            .info(info
                    .contact(contact)
            )
            .components(new Components().addSecuritySchemes("bearer-key",
                    new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
  }

  /**
   * Allow to override default description by a dedicated markdown file which is by default at resources/swagger/API.md
   *
   * @return content of the markdown
   */
  protected String getMarkdownDescription() {
    StringWriter output;
    try (InputStream stream = new ClassPathResource(SWAGGER_MD).getInputStream()) {
      output = new StringWriter();
      IOUtils.copy(stream, output, StandardCharsets.UTF_8);
      return output.toString();
    } catch (IOException e) {
      log.warn("Project does not have a {} file to describe API swagger", SWAGGER_MD);
    }
    return null;
  }
}
