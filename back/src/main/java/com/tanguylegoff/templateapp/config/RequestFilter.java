package com.tanguylegoff.templateapp.config;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;

/**
 * Initialize a fishtag with each request and stores it in MDC
 */
@Component
@Slf4j
public class RequestFilter implements Filter {

  /**
   * Initialize a fishtag with each request and stores it in MDC
   *
   * @param servletRequest  the servlet request
   * @param servletResponse the servlet response
   * @param filterChain     the filter chain
   * @throws IOException      for reasons
   * @throws ServletException for reasons
   */
  @Override
  // No need for a secure random string here, it's only a fishtag
  @SuppressWarnings("java:S2245")
  public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    String shortId = RandomStringUtils.random(8, "0123456789abcdef");
    MDC.put("fishtag", shortId);
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
    ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(
            httpServletResponse
    );
    filterChain.doFilter(servletRequest, responseWrapper);
    responseWrapper.setHeader("fishtag", shortId);
    responseWrapper.copyBodyToResponse();
  }
}