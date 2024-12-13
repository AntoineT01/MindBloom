package com.tanguylegoff.templateapp.config.validation.annotation;

import com.tanguylegoff.templateapp.config.validation.validator.MustBeTheSameAsInPathOrMyselfValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Spring validation annotation validated by {@link MustBeTheSameAsInPathOrMyselfValidator}. <br/>
 * Controls if the current http request is a modification and if the annotated value is the same as the one given as a path parameter.
 */
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MustBeTheSameAsInPathOrMyselfValidator.class)
public @interface MustBeTheSameAsInPathOrMyself {

  /**
   * The error message if invalid
   *
   * @return The error message if invalid
   */
  @SuppressWarnings("unused")
  String message() default "";

  /**
   * Not used, helps ordering validation
   *
   * @return the groups
   */
  @SuppressWarnings("unused")
  Class<?>[] groups() default {};

  /**
   * Not used, helps to specify specific return payloads
   *
   * @return the payloads
   */
  @SuppressWarnings("unused")
  Class<? extends Payload>[] payload() default {};
}
