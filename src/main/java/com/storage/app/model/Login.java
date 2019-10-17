package com.storage.app.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Login {
  @NotNull
  @Size(min = 1, max = 50)
  private String username;

  @NotNull
  @Size(min = 4, max = 100)
  private String password;
}
