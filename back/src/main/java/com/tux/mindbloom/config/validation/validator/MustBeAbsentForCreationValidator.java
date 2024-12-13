package com.tux.mindbloom.config.validation.validator;

import com.tux.mindbloom.config.validation.annotation.MustBeAbsentForCreation;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Spring validator for {@link MustBeAbsentForCreation}. <br/>
 * Controls if the current http request is a modification and if the annotated value is set or not
 */
public class MustBeAbsentForCreationValidator extends ValidatorForRequests implements ConstraintValidator<MustBeAbsentForCreation, Long> {

  /**
   * Injection constructor for {@link MustBeAbsentForCreationValidator}
   *
   * @param request the current request being filtered
   */
  public MustBeAbsentForCreationValidator(HttpServletRequest request) {
    super(request);
  }

  /**
   * This validator checks if the current http request is a modification and if the annotated value is set or not
   *
   * @param value   the annotated value
   * @param context the validation context
   * @return false if this is a modification request and the annotated value is not set, else false
   */
  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();
    if (isACreationRequest() && value != null) {
      context.buildConstraintViolationWithTemplate("ne doit pas être renseigné lors d'une création").addConstraintViolation();
      return false;
    }

    return true;
  }
}
