package com.storage.app.config;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import javax.annotation.Resource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/** @author choang on 10/23/19 */
@ConditionalOnProperty(value = "aws.enabled", havingValue = "true", matchIfMissing = true)
@Configuration
public class AwsClient {
  @Resource private AwsProperties awsProperties;

  @Bean
  public AmazonS3 amazonS3() {
    AWSCredentials credentials =
        new BasicAWSCredentials(awsProperties.getAccessKey(), awsProperties.getSecretKey());

    return AmazonS3ClientBuilder.standard()
        .withCredentials(new AWSStaticCredentialsProvider(credentials))
        .withRegion(awsProperties.getRegion())
        .build();
  }
}
