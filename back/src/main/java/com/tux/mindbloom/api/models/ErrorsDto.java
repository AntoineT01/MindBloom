package com.tux.mindbloom.api.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


/**
 * Holds a list of errors and general information about a failure
 */
@Getter
@Validated
@Schema(description = "Holds a list of errors and general information about a failure")
public class ErrorsDto {

  /**
   * Randomized used in all logs pertaining to a single api call
   */
  @NotNull
  @Size(min = 8, max = 8)
  @Pattern(regexp = "[0-9a-f]*")
  @Schema(description = "Randomized used in all logs pertaining to a single api call", example = "12a4567f")
  private final String fishtag;

  /**
   * The UTC time when this error is created (without nanos)
   */
  @NotNull
  @JsonProperty("utc-time")
  @Size(min = 20, max = 20)
  @SuppressWarnings("java:S5843")
  @Pattern(regexp = "(?:[1-9]\\d{3}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1\\d|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[1-9]\\d(?:0[48]|[2468][048]|[13579][26])|(?:[2468][048]|[13579][26])00)-02-29)T(?:[01]\\d|2[0-3]):[0-5]\\d:[0-5]\\d(?:Z|[+-][01]\\d:[0-5]\\d)")
  @Schema(description = "The UTC time when this error is created (without nanos)", example = "2023-07-19T09:23:00Z")
  private final String utcTime;

  /**
   * Non null list of error details
   */
  @NotEmpty
  @Schema(description = "List of error details")
  private final List<@Valid ErrorDetailDto> errors;

  /**
   * The http status that will be returned with this error
   */
  @NotNull
  @Min(400)
  @Max(599)
  @JsonProperty("http-status")
  @Schema(description = "The http status that will be returned with this error", example = "400")
  private final Integer httpStatus;

  /**
   * Constructs an error with default values for each field
   *
   * @param httpStatus the http status that will be returned with this error
   */
  public ErrorsDto(@NotNull HttpStatus httpStatus) {
    this.utcTime = ZonedDateTime.now(ZoneOffset.UTC).withNano(0).format(DateTimeFormatter.ISO_INSTANT);
    this.fishtag = MDC.get("fishtag");
    this.errors = new ArrayList<>();
    this.httpStatus = httpStatus.value();
  }

  /**
   * Constructs an error with default values for each field, and an error message
   *
   * @param httpStatus the http status that will be returned with this error
   * @param code       the joined error's code
   * @param message    the joined error's message
   */
  public ErrorsDto(@NotNull HttpStatus httpStatus, @NotNull String code, @NotNull String message) {
    this(httpStatus);
    addError(code, message);
  }

  /**
   * Adds an error detail to this error
   *
   * @param code    the error's code
   * @param message the error's message
   */
  public void addError(@NotNull String code, @NotNull String message) {
    this.errors.add(new ErrorDetailDto(code, message));
  }
}