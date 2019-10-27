package com.storage.app.controller;

import com.storage.app.exception.SystemException;
import com.storage.app.model.JwtToken;
import com.storage.app.model.Login;
import com.storage.app.model.User;
import com.storage.app.repository.UserRepository;
import com.storage.app.security.JwtTokenProvider;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** @author choang on 10/22/19 */
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthenticationController {
  private final JwtTokenProvider tokenProvider;
  private final UserRepository userRepository;
  private final AuthenticationManager authenticationManager;

  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(@Valid @RequestBody Login login) {

    UsernamePasswordAuthenticationToken authToken =
        new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

    authenticationManager.authenticate(authToken);

    String username = login.getUsername();

    User user =
        userRepository
            .findOneByUsername(username)
            .orElseThrow(
                () ->
                    new SystemException(
                        "Authentication Error: Cannot find username " + login.getUsername(),
                        HttpStatus.NOT_FOUND));

    JwtToken token = new JwtToken(tokenProvider.createToken(username, user.getRoles()));

    return ResponseEntity.ok(token);
  }
}
