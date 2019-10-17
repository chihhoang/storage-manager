package com.storage.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StorageManagerApplication {
  @Value("${app.env}")
  private String env;

  public static void main(String[] args) {
    SpringApplication.run(StorageManagerApplication.class, args);
  }

  @Bean
  public CommandLineRunner startup() {
    return args -> {
      log.info("========== Starting app... in {} env", env);
    };
  }
}
