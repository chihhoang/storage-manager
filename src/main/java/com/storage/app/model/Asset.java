package com.storage.app.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Asset extends Audit {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String s3Key;
  private String fileName;
  private String version;
  private String firstName;
  private String lastName;

  @Column(name = "description", length = 512)
  private String description;

  @Column(name = "s3_url", length = 512)
  private String s3Url;

  @Column(name = "cloud_front_url", length = 512)
  private String cloudFrontUrl;

  @Column(name = "acceleration_url", length = 512)
  private String accelerationTransferUrl;

  @JsonIgnore
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
