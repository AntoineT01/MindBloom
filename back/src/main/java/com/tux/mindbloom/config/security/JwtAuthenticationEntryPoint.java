package com.tux.mindbloom.config.security;

import com.tux.mindbloom.api.models.ErrorDetailDto;
import com.tux.mindbloom.utils.JacksonPrinter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.Serializable;

/**
 * Incorrect login entrypoint
 * ==> Not used somehow, fix this, so we can customize spring security exceptions
 */
@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

  /**
   * {@inheritDoc}
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {

    // We cannot use @ControllerAdvice to handle spring security errors, since we are blocked in a filter before reaching the controllers
    log.error("Error : Unauthorized - Please provide valid credentials");
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.getOutputStream().println(JacksonPrinter.toJson(new ErrorDetailDto("Unauthorized", "Please provide valid credentials")));
  }
}