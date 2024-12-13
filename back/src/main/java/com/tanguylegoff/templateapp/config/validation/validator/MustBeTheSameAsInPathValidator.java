package com.tanguylegoff.templateapp.config.validation.validator;

import com.tanguylegoff.templateapp.config.validation.annotation.MustBeTheSameAsInPath;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Spring validator for {@link MustBeTheSameAsInPath}. <br/>
 * Controls if the current http request is a modification and if the annotated value is the same as the one given as a path parameter.
 */
public class MustBeTheSameAsInPathValidator extends ValidatorForRequests implements ConstraintValidator<MustBeTheSameAsInPath, Long> {

  /**
   * Injection constructor for {@link MustBeTheSameAsInPathValidator}
   *
   * @param request the current request being filtered
   */
  public MustBeTheSameAsInPathValidator(HttpServletRequest request) {
    super(request);
  }

  /**
   * This validator checks if the current http request is a modification and if the annotated value is the same as the one given as a path parameter
   *
   * @param value   the annotated value
   * @param context the validation context
   * @return false if this is a modification request and the annotated value is not set, else false
   */
  @Override
  public boolean isValid(Long value, ConstraintValidatorContext context) {
    context.disableDefaultConstraintViolation();

    if (isAModificationRequest() && hasValueDifferentFromTheOneGivenInPathParam(value)) {
      context.buildConstraintViolationWithTemplate("doit être le même que celui renseigné dans le path lors d'une modification").addConstraintViolation();
      return false;
    }

    return true;
  }
}
