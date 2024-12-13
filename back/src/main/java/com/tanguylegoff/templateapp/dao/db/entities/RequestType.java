package com.tanguylegoff.templateapp.dao.db.entities;

public enum RequestType {
  // Setup password during signup (before logging in)
  SIGNUP,
  // Change password (once logged on)
  CHANGE,
  // Reset password (logged out)
  RESET,
  // Verify mail address (signup and updating mail)
  VERIFYMAIL,
}
