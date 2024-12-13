package com.tux.mindbloom.api.exceptions;

import com.tux.mindbloom.api.models.ErrorsDto;
import com.tux.mindbloom.config.exceptions.BadArgumentException;
import com.tux.mindbloom.config.exceptions.MailAlreadyExistsException;
import com.tux.mindbloom.utils.JacksonPrinter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * This is the exception handler, catches all runtimes thrown in the api and formats them before returning them to the client
 */
@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler {

  /**
   * Handles MethodArgumentNotValidException
   *
   * @param ex the exception to handle
   * @return the formatted error
   */
  @ExceptionHandler({MethodArgumentNotValidException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsDto handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ErrorsDto error = new ErrorsDto(HttpStatus.BAD_REQUEST);

    ex.getBindingResult().getAllErrors().forEach(bindingError -> {
        // It's actually here a FieldError. Might not be true in the future...
        FieldError fe = (FieldError) bindingError;
        // Very small list, don't use StringBuilder here
        error.addError(FieldError.class.getSimpleName(), camelToKebabCase(fe.getField()) + ": [" + fe.getRejectedValue() + "] " + fe.getDefaultMessage());
      }
    );

    return logError(MethodArgumentNotValidException.class, error);
  }

  /**
   * Handles AccessDeniedException
   *
   * @param ex the exception to handle
   * @return the formatted error
   */
  @ExceptionHandler({AccessDeniedException.class})
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  public ErrorsDto handleAccessDeniedException(AccessDeniedException ex) {
    String message = StringUtils.isBlank(ex.getMessage()) ? "Access denied" : ex.getMessage();
    return handleMostErrors(HttpStatus.UNAUTHORIZED, AccessDeniedException.class, ex, message);
  }

  /**
   * Handles HandleAlreadyExistsException
   *
   * @param ex the exception to handle
   * @return the formatted error
   */
  @ExceptionHandler({MailAlreadyExistsException.class})
  @ResponseStatus(HttpStatus.CONFLICT)
  public ErrorsDto handleBadRequestException(MailAlreadyExistsException ex) {
    String message = StringUtils.isBlank(ex.getMessage()) ? "Conflict" : ex.getMessage();
    return handleMostErrors(HttpStatus.CONFLICT, MailAlreadyExistsException.class, ex, message);
  }

  /**
   * Handles BadArgumentException
   *
   * @param ex the exception to handle
   * @return the formatted error
   */
  @ExceptionHandler({BadArgumentException.class})
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ErrorsDto handleBadRequestException(BadArgumentException ex) {
    String message = StringUtils.isBlank(ex.getMessage()) ? "Bad request" : ex.getMessage();
    return handleMostErrors(HttpStatus.BAD_REQUEST, BadArgumentException.class, ex, message);
  }

  /**
   * This is a fallback for any non yet handled exception
   *
   * @param ex the exception to handle
   * @return the formatted error
   */
  @ExceptionHandler({Throwable.class})
  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  public ErrorsDto handleThrowable(Exception ex) {
    String message = StringUtils.isBlank(ex.getMessage()) ? "Internal server error" : ex.getMessage();
    return handleMostErrors(HttpStatus.INTERNAL_SERVER_ERROR, Throwable.class, ex, message);
  }

  /**
   * Map the raised exception to the error format returned by this api and logs it
   *
   * @param httpStatus the http status to return with the error
   * @param clazz      the class of the raised exception
   * @param ex         the raised exception
   * @param message    the message to return for this error
   * @param <T>        the type of the class of the raised exception
   * @return the built error
   */
  private <T> ErrorsDto handleMostErrors(HttpStatus httpStatus, Class<T> clazz, T ex, String message) {
    return logError(clazz, new ErrorsDto(httpStatus, ex.getClass().getSimpleName(), message));
  }

  /**
   * Logs the returned error and returns it
   *
   * @param clazz the class of the raised exception
   * @param error the error itself
   * @param <T>   the type of the class of the raised exception
   * @return the error itself
   */
  private <T> ErrorsDto logError(Class<T> clazz, ErrorsDto error) {
    // We SHOULD log the exception (at least the partial stack), but we'll trust our mapping instead
    log.error("{} : \n{}", clazz.getSimpleName(), JacksonPrinter.toJson(error));
    return error;
  }

  /**
   * Maps a camelCase string to kebab-case
   *
   * @param str the camelCase string
   * @return the mapped kebab-case
   */
  private String camelToKebabCase(String str) {
    return str.replaceAll("([a-z0-9])([A-Z])", "$1-$2").toLowerCase();
  }

}
