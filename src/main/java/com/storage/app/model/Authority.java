package com.storage.app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Entity
@Data
public class Authority {
  @NotNull
  @Size(max = 50)
  @Id
  @Column(length = 50)
  private String name;
}
