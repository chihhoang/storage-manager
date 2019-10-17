package com.storage.app.model;

public enum Role {
  ADMIN("ADMIN"),
  USER("USER");

  private final String value;

  Role(String value) {
    this.value = value;
  }

  public String value() {
    return this.value;
  }

  @Override
  public String toString() {
    return this.value();
  }
}
