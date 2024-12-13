package com.tux.mindbloom.config.validation.validator;

import com.tux.mindbloom.config.validation.annotation.MustBeTheSameAsInPathOrMyself;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Spring validator for {@link MustBeTheSameAsInPathOrMyself}. <br/>
 * Controls if the current http request is a modification and if the annotated value is the same as the one given as a path parameter.
 */
public class MustBeTheSameAsInPathOrMyselfValidator extends ValidatorForRequests implements ConstraintValidator<MustBeTheSameAsInPathOrMyself, Long> {

  /**
   * Injection constructor for {@link MustBeTheSameAsInPathOrMyselfValidator}
   *
   * @param request the current request being filtered
   */
  public MustBeTheSameAsInPathOrMyselfValidator(HttpServletRequest request) {
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

    if (isAModificationRequest() && hasValueDifferentFromTheOneGivenInPathParamOrMyself(value)) {
      context.buildConstraintViolationWithTemplate("doit être le même que celui renseigné dans le path lors d'une modification").addConstraintViolation();
      return false;
    }

    return true;
  }
}
