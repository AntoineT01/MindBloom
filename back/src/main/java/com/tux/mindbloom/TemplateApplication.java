package com.tux.mindbloom;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.TimeZone;

/**
 * Main app
 */
@SpringBootApplication
@ComponentScan(basePackages = {
        "com.tux.mindbloom.api",
        "com.tux.mindbloom.business",
        "com.tux.mindbloom.dao",
        "com.tux.mindbloom.config"
})
@Slf4j
public class TemplateApplication {

  /**
   * The main app
   *
   * @param args its args
   */
  public static void main(String[] args) {
    // Force TZ before @PostConstruct, it's useful sometimes
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
    SpringApplication.run(TemplateApplication.class, args);
    log.info("Swagger on http://localhost:8081/swagger-ui.html");
  }

  /**
   * Forces Timezone for app.
   * We don't want a disconnect between backend and database for timezones.
   */
  @PostConstruct
  public void init() {
    TimeZone.setDefault(TimeZone.getTimeZone("Europe/Paris"));
  }
}
