package com.storage.app.model;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {
  ROLE_ADMIN("ROLE_ADMIN"),
  ROLE_USER("ROLE_USER");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

  @Override
  public String getAuthority() {
    return name();
  }
}
