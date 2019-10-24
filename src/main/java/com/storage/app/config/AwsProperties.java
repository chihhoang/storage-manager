package com.storage.app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/** @author choang on 10/23/19 */
@Configuration
@ConfigurationProperties(prefix = AwsProperties.PREFIX)
@Getter
@Setter
public class AwsProperties {
  static final String PREFIX = "aws";

  private String region;
  private String accessKey;
  private String secretKey;
  private String s3Bucket;
  private String s3Url;
  private String cloudFrontUrl;
  private String s3AccelerateUrl;
}
