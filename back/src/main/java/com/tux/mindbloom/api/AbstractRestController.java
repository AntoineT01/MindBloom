package com.tux.mindbloom.api;

import com.tux.mindbloom.utils.JacksonPrinter;
import org.slf4j.Logger;

/**
 * Shares logging mechanics across controllers
 */
public abstract class AbstractRestController {

  /**
   * Logs a request if it's allowed
   *
   * @param log     the logger
   * @param request the request
   * @param <T>     the request type
   */
  protected <T> void logRequest(Logger log, T request) {
    if (log.isDebugEnabled()) {
      log.debug("Request body :\n{}", JacksonPrinter.toJson(request));
    }
  }

  /**
   * Logs a response status and traces the response itself if it's allowed
   *
   * @param log      the logger
   * @param method   the http method
   * @param response the response
   * @param <T>      the response type
   * @return the unaltered response
   */
  protected <T> T logResponse(Logger log, String method, T response) {
    logResponse(log, method);
    if (log.isTraceEnabled()) {
      // C'est beaucoup trop verbeux, je descends le niveau de log de debug à TRACE
      log.trace("Response :\n{}", JacksonPrinter.toJson(response));
    }
    return response;
  }

  /**
   * Logs a response status
   *
   * @param log    the logger
   * @param method the http response
   */
  protected void logResponse(Logger log, String method) {
    log.info("{} Success", method);
  }

}
