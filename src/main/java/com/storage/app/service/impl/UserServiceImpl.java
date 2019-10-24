package com.storage.app.service.impl;

import com.storage.app.exception.SystemException;
import com.storage.app.mapper.UserMapper;
import com.storage.app.model.Login;
import com.storage.app.model.User;
import com.storage.app.model.UserDTO;
import com.storage.app.repository.UserRepository;
import com.storage.app.security.JwtTokenProvider;
import com.storage.app.service.UserService;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;
  private final JwtTokenProvider tokenProvider;

  @Override
  public List<User> getAll() {
    return userRepository.findAll();
  }

  @Override
  public User createUser(UserDTO input) {
    User user = userMapper.toUser(input);

    user.setPassword(passwordEncoder.encode(input.getPassword()));

    log.debug("Created User: {}", user);

    return userRepository.save(user);
  }

  @Override
  public User getUserById(long id) {
    User user = userRepository.getOne(id);

    if (user == null) {
      throw new SystemException("User not found with id " + id, HttpStatus.NOT_FOUND);
    }

    return user;
  }

  @Override
  public User getUserByUsername(String username) {
    return userRepository
        .findOneByLogin(username)
        .orElseThrow(
            () ->
                new SystemException("Unable to find username " + username, HttpStatus.BAD_REQUEST));
  }

  @Override
  public User whoami(HttpServletRequest request) {
    String username = tokenProvider.getUserLogin(tokenProvider.resolveToken(request));

    return userRepository
        .findOneByLogin(username)
        .orElseThrow(() -> new SystemException("User not found", HttpStatus.NOT_FOUND));
  }

  @Override
  public User updateUser(Login login) {
    Optional<User> user = userRepository.findOneByLogin(login.getUsername());

    if (!user.isPresent()) {
      throw new SystemException(
          "User not found with username " + login.getUsername(), HttpStatus.NOT_FOUND);
    }

    // Just update/reset password for now
    user.get().setPassword(passwordEncoder.encode(login.getPassword()));

    return user.get();
  }
}
