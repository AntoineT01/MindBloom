package com.tanguylegoff.templateapp.api.impl;

import com.tanguylegoff.templateapp.api.FormLoginController;
import com.tanguylegoff.templateapp.api.models.FormLoginDto;
import org.springframework.web.bind.annotation.RestController;

/**
 * implementation of {@link FormLoginController}
 * This is only kept to make endpoint appear in swagger
 */
@RestController
public class FormLoginControllerImpl implements FormLoginController {

  /**
   * {@inheritDoc}
   */
  @Override
  public void formLoginProcessor(FormLoginDto body) {
    throw new IllegalStateException("This method shouldn't be called. It's implemented by Spring Security filters.");
  }
}
