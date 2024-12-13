package com.tux.mindbloom.config.env;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;

@Configuration
public class EnvConfig {

  public void loadEnv() {
    Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();

    dotenv.entries().forEach(entry -> {
      System.setProperty(entry.getKey(), entry.getValue());
    });
  }
}
