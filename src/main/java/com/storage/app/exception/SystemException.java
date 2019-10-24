package com.storage.app.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@RequiredArgsConstructor
@Getter
public class SystemException extends RuntimeException {
  private static final long serialVersionUID = 140591408707087290L;

  private final String message;
  private final HttpStatus httpStatus;
}
