package com.tux.mindbloom.config.validation.validator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapLikeType;
import com.fasterxml.jackson.databind.type.TypeFactory;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.HandlerMapping;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;

/**
 * Abstract holder for validator tools that require a {@link HttpServletRequest}.
 */
abstract class ValidatorForRequests {

  /**
   * The current request being filtered
   */
  final HttpServletRequest request;

  /**
   * A Jackson mapper for type conversion
   */
  final ObjectMapper mapper;

  /**
   * A shared type for path params
   */
  final MapLikeType pathParamsType;

  /**
   * Injection constructor for {@link ValidatorForRequests}
   *
   * @param request the current request being filtered
   */
  ValidatorForRequests(HttpServletRequest request) {
    this.request = request;
    this.mapper = new ObjectMapper();
    this.pathParamsType = TypeFactory.defaultInstance().constructMapLikeType(Map.class, String.class, Object.class);
  }

  /**
   * Checks if the current request is a creation request
   *
   * @return true if it is, else false
   */
  boolean isACreationRequest() {
    return HttpMethod.POST.matches(request.getMethod());
  }

  /**
   * Checks if the current request is a modification request
   *
   * @return true if it is, else false
   */
  boolean isAModificationRequest() {
    return Arrays.asList(HttpMethod.PUT.name(), HttpMethod.PATCH.name()).contains(request.getMethod());
  }

  /**
   * Checks if the annotated field's value is the same as the one given in the request's path param
   *
   * @param value The annotated field's value
   * @return true if they are different, else false
   */
  boolean hasValueDifferentFromTheOneGivenInPathParam(Long value) {
    // We use Jackson to prevent raw usage of parameterized types during casts
    Map<String, Object> pathParams = mapper.convertValue(
            request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE),
            pathParamsType
    );
    Long pathId = mapper.convertValue(pathParams.get("id"), Long.class);

    return !Objects.equals(value, pathId);
  }


  /**
   * Checks if the annotated field's value is the same as the one given in the request's path param
   *
   * @param value The annotated field's value
   * @return true if they are different, else false
   */
  boolean hasValueDifferentFromTheOneGivenInPathParamOrMyself(Long value) {
    boolean pathIsForMyself = request.getRequestURI().matches("^.*/me(/.*)?");
    return !pathIsForMyself && hasValueDifferentFromTheOneGivenInPathParam(value);
  }
}
