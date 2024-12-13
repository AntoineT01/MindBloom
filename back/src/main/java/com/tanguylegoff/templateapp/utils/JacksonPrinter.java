package com.tanguylegoff.templateapp.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

/**
 * Tool for pretty printing objects
 */
@Slf4j
public class JacksonPrinter {

  /**
   * L'instance de printer jackson
   */
  private static ObjectMapper mapper;

  /**
   * Cache le constructeur
   */
  private JacksonPrinter() {

  }

  /**
   * Instancie ou retourne l'instance de printer jackson
   *
   * @return l'instance de printer jackson
   */
  private static ObjectMapper getMapper() {
    if (mapper == null) {
      mapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT).registerModule(new JavaTimeModule());
    }

    return mapper;
  }

  public static String toJson(Object o) {
    try {
      return getMapper().writerWithDefaultPrettyPrinter().writeValueAsString(o);
    } catch (JsonProcessingException e) {
      log.warn("Could not pretty-print json. Returning null", e);
      return null;
    }
  }
}
