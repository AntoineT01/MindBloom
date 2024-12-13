package com.tanguylegoff.templateapp.api.models;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.validation.annotation.Validated;

/**
 * Holds an error's detail
 */
@Getter
@AllArgsConstructor
@Validated
@Schema(description = "Holds an error's details")
public class ErrorDetailDto {

  /**
   * Error code, usually it's java class name
   */
  @NotNull
  @Schema(description = "Error code, usually it's java class name", example = "FieldError")
  private final String code;

  /**
   * Error message, should be in english but spring generates localized messages
   */
  @NotNull
  @Schema(description = "Error message, should be in english but spring generates localized messages", example = "id: [1] ne doit pas être renseigné lors d'une création")
  private final String message;
}