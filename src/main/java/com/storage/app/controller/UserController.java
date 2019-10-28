package com.storage.app.controller;

import com.storage.app.mapper.UserMapper;
import com.storage.app.model.Login;
import com.storage.app.model.User;
import com.storage.app.model.UserDTO;
import com.storage.app.security.JwtTokenProvider;
import com.storage.app.service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin("*")
@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserService userService;
  private final UserMapper userMapper;
  private final JwtTokenProvider tokenProvider;

  /**
   * Get all users. Only available to ADMIN
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body all users.
   */
  @GetMapping("/admin/users")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> getAllUsers() {
    return ResponseEntity.ok(userService.getAll());
  }

  /**
   * Register a user
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body created user
   *     information.
   */
  @PostMapping("/users/signup")
  public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDto) {
    userDto = userMapper.toDtoUser(userService.createUser(userDto));

    String token = tokenProvider.createToken(userDto.getUsername(), userDto.getRoles());

    userDto.setIdToken(token);

    return ResponseEntity.ok(userDto);
  }

  @GetMapping(value = "/users/me")
  public User whoami(HttpServletRequest request) {
    return userService.whoami(request);
  }

  /**
   * Reset password for a user. Only available to ADMIN
   *
   * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with success message
   */
  @PostMapping("/admin/users/reset-password")
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<?> resetPassword(@Valid @RequestBody Login login) {
    userService.updateUser(login);

    return ResponseEntity.ok("Password updated successful.");
  }
}
