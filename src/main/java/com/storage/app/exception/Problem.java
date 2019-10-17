package com.storage.app.exception;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class Problem {
  private String code;
  private String message;
}
