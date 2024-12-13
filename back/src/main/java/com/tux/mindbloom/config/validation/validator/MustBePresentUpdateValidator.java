package com.tux.mindbloom.config.validation.validator;

import com.tux.mindbloom.config.validation.annotation.MustBePresentForUpdate;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * Spring validator for {@link MustBePresentForUpdate}. <br/>
 * Controls if the current http request is a modification and if the annotated value is set or not
 */
public class MustBePresentUpdateValidator extends ValidatorForRequests implements ConstraintValidator<MustBePresentForUpdate, Long> {

  /**
   * Injection constructor for {@link MustBePresentUpdateValidator}
   *
   * @param request the current request being filtered
   */
  public MustBePresentUpdateValidator(HttpServletRequest request) {
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
    if (isAModificationRequest() && value == null) {
      context.buildConstraintViolationWithTemplate("doit être renseigné lors d'une modification").addConstraintViolation();
      return false;
    }

    return true;
  }
}
