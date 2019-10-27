package com.storage.app;

import com.google.common.collect.Sets;
import com.storage.app.model.Role;
import com.storage.app.model.UserDTO;
import com.storage.app.repository.UserRepository;
import com.storage.app.service.UserService;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;

@Slf4j
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class StorageManagerApplication {
  @Value("${app.env}")
  private String env;

  @Resource private UserService userService;
  @Resource private UserRepository userRepository;

  public static void main(String[] args) {
    SpringApplication.run(StorageManagerApplication.class, args);
  }

  @Bean
  public CommandLineRunner startup() {
    return args -> {
      log.info("========== Starting app... in {} env", env);

      if (!userRepository.findOneByUsername("admin").isPresent()) {
        UserDTO admin =
            UserDTO.builder()
                .username("admin")
                .password("admin")
                .email("chhoang102@gmail.com")
                .activated(true)
                .createdBy("admin")
                .firstName("Chi")
                .lastName("Hoang")
                .imageUrl("http://example.com")
                .roles(Sets.newHashSet(Role.ROLE_ADMIN, Role.ROLE_USER))
                .build();

        userService.createUser(admin);
      }
    };
  }
}
