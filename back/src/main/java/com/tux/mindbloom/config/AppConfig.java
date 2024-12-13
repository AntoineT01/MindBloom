package com.tux.mindbloom.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class AppConfig {

  private final String accountVerifyPath = "/verify-email";
  @Value("${app.base-url}")
  private String baseUrl;

}
