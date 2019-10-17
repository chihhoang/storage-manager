package com.storage.app.exception;

public class SystemException extends RuntimeException {

  private static final long serialVersionUID = 140591408707087290L;

  public SystemException(final String message) {
    super(message);
  }

  public SystemException(final Throwable t) {
    super(t);
  }

  public SystemException(final String message, final Throwable t) {
    super(message, t);
  }
}
