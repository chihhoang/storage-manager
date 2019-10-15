package com.storage.app;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin
@Slf4j
@SpringBootApplication
public class StorageManagerApplication {
  @Value("${app.env}")
  private String env;

  @Autowired private Environment environment;

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
