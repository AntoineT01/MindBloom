package com.tanguylegoff.templateapp.config.security.impl;

import com.tanguylegoff.templateapp.config.security.CurrentAccountProvider;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class CurrentAccountProviderImpl implements CurrentAccountProvider {

  public Long get() {
    String id = SecurityContextHolder.getContext().getAuthentication().getName();
    if (id == null) {
      return null;
    }
    return Long.valueOf(id);
  }
}
